package com.smartmenu.order.service;

import com.smartmenu.client.order.OrderRequest;
import com.smartmenu.client.order.OrderResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.user.db.entity.User;
import com.smartmenu.order.db.entity.Order;
import com.smartmenu.order.db.repository.OrderRepository;
import com.smartmenu.order.mapper.OrderMapper;
import com.smartmenu.order.mapper.OrderUpdateMapper;
import com.smartmenu.orderitem.db.entity.OrderItem;
import com.smartmenu.orderitem.db.repository.OrderItemRepository;
import com.smartmenu.orderitem.enums.OrderItemState;
import com.smartmenu.orderitem.mapper.OrderItemMapper;
import com.smartmenu.orderitem.service.OrderItemService;
import com.smartmenu.rtable.db.entity.RTable;
import com.smartmenu.rtable.db.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class OrderService extends AbstractBaseService<OrderRequest, Order, OrderResponse, OrderMapper> {
	final private OrderRepository repository;
	final private OrderMapper mapper;
	final private OrderUpdateMapper updateMapper;
	final private TableRepository tableRepository;
	final private OrderItemRepository orderItemRepository;
	final private OrderItemMapper orderItemMapper;
	final private OrderItemService orderItemService;

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

			if (table.getIsOpen()) {
				savedOrder = repository.getOne(table.getOrder().getId());
				List<OrderItem> orderItems = orderItemMapper.toEntity(request.getOrderItems());
				for (OrderItem orderItem : orderItems) { orderItem.setOrderId(savedOrder.getId()); }
				orderItemService.updateTotalPrice(orderItems);
				orderItemService.setInitialState(orderItems);
				savedOrder.getOrderItems().addAll(orderItems);
				updateTotalPrice(savedOrder);
				savedOrder = repository.save(savedOrder);
			} else {
				Order entityToSave = getMapper().toEntity(request);
				List<OrderItem> orderItems = entityToSave.getOrderItems();

				entityToSave.setBrand(table.getBrand());
				entityToSave.setTableId(table.getId());
				orderItemService.updateTotalPrice(orderItems);
				orderItemService.setInitialState(orderItems);
				updateTotalPrice(entityToSave);
				entityToSave.setOrderItems(null);
				savedOrder = repository.save(entityToSave);
				for (OrderItem orderItem : orderItems) { orderItem.setOrderId(savedOrder.getId()); }
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

	private void updateTotalPrice(Order order) {
		order.setTotalPrice(order.getOrderItems()
				.stream()
				.map(OrderItem::getTotalPrice)
				.filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add));
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
			repository.save(order);

			//TODO masa kapatildiginda order ve order itemler posife tasinacak
			if (order.getOrderItems().size() == 0) {
				RTable table = tableRepository.getOne(order.getTableId());
				table.setIsOpen(false);
				tableRepository.save(table);
			}

			return new ServiceResult<>(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}
}
