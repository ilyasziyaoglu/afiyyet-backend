package com.afiyyet.order.service;

import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.brand.service.BrandService;
import com.afiyyet.client.order.OrderRequest;
import com.afiyyet.client.order.OrderResponse;
import com.afiyyet.client.order.OrderSummary;
import com.afiyyet.client.product.ProductSummary;
import com.afiyyet.common.basemodel.service.AbstractBaseService;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.common.user.db.entity.User;
import com.afiyyet.order.criteria.OrderCriteria;
import com.afiyyet.order.db.entity.Order;
import com.afiyyet.order.db.repository.OrderRepository;
import com.afiyyet.order.mapper.OrderMapper;
import com.afiyyet.order.mapper.OrderUpdateMapper;
import com.afiyyet.order.utils.OrderUtils;
import com.afiyyet.orderitem.db.entity.OrderItem;
import com.afiyyet.orderitem.db.repository.OrderItemRepository;
import com.afiyyet.orderitem.enums.OrderItemState;
import com.afiyyet.orderitem.mapper.OrderItemMapper;
import com.afiyyet.orderitem.service.OrderItemService;
import com.afiyyet.rtable.db.entity.RTable;
import com.afiyyet.rtable.db.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.LongFilter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class OrderService extends AbstractBaseService<OrderRequest, Order, OrderResponse, OrderMapper> {
	private final OrderRepository repository;
	private final OrderMapper mapper;
	private final OrderUpdateMapper updateMapper;
	private final TableRepository tableRepository;
	private final OrderItemRepository orderItemRepository;
	private final OrderItemMapper orderItemMapper;
	private final OrderItemService orderItemService;
	private final OrderUtils orderUtils;
	private final OrderQueryService orderQueryService;
	private final BrandService brandService;

	@Override
	public OrderRepository getRepository() {
		return repository;
	}

	@Override
	public OrderMapper getMapper() {
		return mapper;
	}

	@Override
	public OrderUpdateMapper getUpdateMapper() {
		return updateMapper;
	}

	public ServiceResult<OrderResponse> get(Long id) {
		try {
			return new ServiceResult<>(mapper.toResponse(repository.getOne(id)));
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<OrderResponse> insert(OrderRequest request) {
		try {
			Order savedOrder;
			RTable table = tableRepository.getOne(request.getTableId());

			if (BooleanUtils.isTrue(table.getIsOpen())) {
				savedOrder = table.getOrder();
				List<OrderItem> orderItems = orderItemMapper.toEntity(request.getOrderItems());
				for (OrderItem orderItem : orderItems) { orderItem.setOrder(savedOrder); }
				orderItemService.updateTotalPrice(orderItems);
				orderItemService.setInitialState(orderItems);
				savedOrder.getOrderItems().addAll(orderItems);
				orderUtils.updateTotalPrice(savedOrder);
				savedOrder = repository.save(savedOrder);
			} else {
				Order entityToSave = getMapper().toEntity(request);
				List<OrderItem> orderItems = entityToSave.getOrderItems();

				entityToSave.setBrand(table.getBrand());
				entityToSave.setTable(table);
				orderItemService.updateTotalPrice(orderItems);
				orderItemService.setInitialState(orderItems);
				orderUtils.updateTotalPrice(entityToSave);
				entityToSave.setOrderItems(null);
				savedOrder = repository.save(entityToSave);
				for (OrderItem orderItem : orderItems) { orderItem.setOrder(savedOrder); }
				savedOrder.setOrderItems(orderItems);
				table.setOrder(savedOrder);
			}

			table.setIsOpen(true);
			tableRepository.save(table);

			return new ServiceResult<>(mapper.toResponse(savedOrder), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<OrderResponse> update(String token, OrderRequest request) {
		try {
			User user = getUser(token);
			Order entity = repository.getOne(request.getId());
			if (!isNotAdmin(user) && !entity.getBrand().getId().equals(user.getBrand().getId())) {
				return forbidden();
			}
			Order newEntity = getUpdateMapper().toEntityForUpdate(request, entity);
			return new ServiceResult<>(mapper.toResponse(repository.save(newEntity)));
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<Boolean> delete(String token, Long id) {
		try {
			User user = getUser(token);
			Order entity = repository.getOne(id);
			if (!isNotAdmin(user) && !entity.getBrand().getId().equals(user.getBrand().getId())) {
				return forbiddenBoolean();
			}
			repository.delete(entity);
			return new ServiceResult<>(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<Boolean> cancel(String token, Long id) {
		try {
			User user = getUser(token);
			Order order = repository.getOne(id);

			if (!isNotAdmin(user) && !isManager(user) && !order.getBrand().getId().equals(user.getBrand().getId())) {
				return forbiddenBoolean();
			}

			Iterator<OrderItem> iterator = order.getOrderItems().iterator();
			while (iterator.hasNext()) {
				OrderItem orderItem = iterator.next();
				orderItem.setState(OrderItemState.CANCELLED);
				orderItem.setCancelReason("Yönetim tarafından tüm sipariş iptal edildi!");
				orderItemRepository.save(orderItem);
				iterator.remove();
			}

			//TODO masa kapatildiginda order ve order itemler posife tasinacak
			if (order.getOrderItems().size() == 0) {
				order.setTotalPrice(BigDecimal.ZERO);
				RTable table = order.getTable();
				table.setIsOpen(false);
				tableRepository.save(table);
			}
			repository.save(order);

			return new ServiceResult<>(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public Page<OrderResponse> findByCriteria(String token, OrderCriteria criteria, Pageable pageable) {
		LongFilter brandFilter = new LongFilter();
		brandFilter.setEquals(getUser(token).getBrand().getId());
		criteria.setBrandId(brandFilter);
		return orderQueryService.findByCriteria(criteria, pageable);
	}

	public OrderSummary summary(String token, OrderCriteria criteria) {
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "createdDate"));
		Page<OrderResponse> orders = findByCriteria(token, criteria, pageable);
		OrderSummary orderSummary = new OrderSummary();
		if (orders.getTotalElements() == 0) {
			return orderSummary;
		}
		orderSummary.setCount(orders.getTotalElements());
		BigDecimal total = orders.getContent().stream().map(OrderResponse::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
		orderSummary.setTotal(total.doubleValue());
		orderSummary.setMin(orders.getContent().stream().map(OrderResponse::getTotalPrice).min(Comparator.comparing(BigDecimal::doubleValue)).orElse(BigDecimal.ZERO).doubleValue());
		orderSummary.setAvg(total.divide(BigDecimal.valueOf(orders.getTotalElements()), 2, RoundingMode.FLOOR).doubleValue());
		orderSummary.setMax(orders.getContent().stream().map(OrderResponse::getTotalPrice).max(Comparator.comparing(BigDecimal::doubleValue)).orElse(BigDecimal.ZERO).doubleValue());

		Map<String, ProductSummary> productSummaries = new HashMap<>();
		orders.stream().flatMap(order -> order.getOrderItems().stream()).forEach(orderItem -> {
			String productName = orderItem.getProduct().getName();
			ProductSummary productSummary = productSummaries.get(productName);

			if (Objects.isNull(productSummary)) {productSummary = new ProductSummary();}

			productSummary.setName(productName);
			productSummary.setCount(productSummary.getCount() + orderItem.getAmount() * orderItem.getPortion());
			productSummary.setTotal(productSummary.getTotal() + orderItem.getTotalPrice().doubleValue());

			productSummaries.put(
				productName,
				productSummary
			);
		});

		orderSummary.setProductSummaries(productSummaries.values().stream().sorted(Comparator.comparingDouble(ProductSummary::getTotal).reversed()).toList());

		return orderSummary;
	}

	public Double roundPerTablePerHour(String token, OrderCriteria criteria) {
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		Page<OrderResponse> orders = findByCriteria(token, criteria, pageable);
		if (orders.getTotalElements() == 0) {
			return 0.0;
		}

		Brand brand = getUser(token).getBrand();

		return orders.getTotalElements() / (double) ((long) brand.getTableCount() * brand.getOpenDuration());
	}

	public Map<Integer, List<Double>> hourlyDsitribution(String token, OrderCriteria criteria) {
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		Page<OrderResponse> orders = findByCriteria(token, criteria, pageable);
		if (orders.getTotalElements() == 0) {
			return new HashMap<>();
		}

		return groupByHour(orders.getContent());
	}

	public Map<Integer, List<Double>> groupByHour(List<OrderResponse> siparisler) {
		Map<Integer, List<Double>> grupluSiparisler = new HashMap<>();

		for (OrderResponse order : siparisler) {
			int hour = order.getCreatedDate().truncatedTo(ChronoUnit.HOURS).getHour();
			grupluSiparisler.computeIfAbsent(hour, k -> new ArrayList<>()).add(order.getTotalPrice().doubleValue());
		}

		return grupluSiparisler;
	}
}
