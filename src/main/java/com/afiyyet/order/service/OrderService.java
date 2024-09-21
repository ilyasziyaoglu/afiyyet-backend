package com.afiyyet.order.service;

import com.afiyyet.client.order.OrderRequest;
import com.afiyyet.client.order.OrderResponse;
import com.afiyyet.common.basemodel.service.AbstractBaseService;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.common.user.db.entity.User;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

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

			if (BooleanUtils.isTrue(table.getOpen())) {
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

			table.setOpen(true);
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
				table.setOpen(false);
				tableRepository.save(table);
			}
			repository.save(order);

			return new ServiceResult<>(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}
}
