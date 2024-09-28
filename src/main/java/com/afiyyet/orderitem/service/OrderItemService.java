package com.afiyyet.orderitem.service;

import com.afiyyet.client.orderitem.OrderItemCancelRequest;
import com.afiyyet.client.orderitem.OrderItemRequest;
import com.afiyyet.client.orderitem.OrderItemResponse;
import com.afiyyet.common.basemodel.service.AbstractBaseService;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.common.user.db.entity.User;
import com.afiyyet.order.db.entity.Order;
import com.afiyyet.order.db.repository.OrderRepository;
import com.afiyyet.order.utils.OrderUtils;
import com.afiyyet.orderitem.db.entity.OrderItem;
import com.afiyyet.orderitem.db.repository.OrderItemRepository;
import com.afiyyet.orderitem.enums.OrderItemState;
import com.afiyyet.orderitem.mapper.OrderItemMapper;
import com.afiyyet.orderitem.mapper.OrderItemUpdateMapper;
import com.afiyyet.rtable.db.entity.RTable;
import com.afiyyet.rtable.db.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class OrderItemService extends AbstractBaseService<OrderItemRequest, OrderItem, OrderItemResponse, OrderItemMapper> {
	private final OrderItemRepository repository;
	private final OrderItemMapper mapper;
	private final OrderItemUpdateMapper updateMapper;
	private final TableRepository tableRepository;
	private final OrderRepository orderRepository;
	private final OrderUtils orderUtils;

	@Override
	public OrderItemRepository getRepository() {
		return repository;
	}

	@Override
	public OrderItemMapper getMapper() {
		return mapper;
	}

	@Override
	public OrderItemUpdateMapper getUpdateMapper() {
		return updateMapper;
	}

	public ServiceResult<OrderItemResponse> get(Long id) {
		try {
			return new ServiceResult<>(mapper.toResponse(repository.getOne(id)));
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<OrderItemResponse> insert(OrderItemRequest request) {
		try {
			OrderItem entityToSave = getMapper().toEntity(request);
			entityToSave.setState(OrderItemState.PREPARING);
			OrderItem entity = repository.save(entityToSave);
			return new ServiceResult<>(mapper.toResponse(entity), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public void updateTotalPrice(List<OrderItem> orderItems) {
		orderItems.forEach(orderItem -> orderItem.setTotalPrice(
				orderItem.getProduct().getPrice()
				.multiply(BigDecimal.valueOf(orderItem.getPortion()))
				.multiply(BigDecimal.valueOf(orderItem.getAmount()))));
	}

	public void setInitialState(List<OrderItem> orderItems) {
		orderItems.forEach(orderItem -> orderItem.setState(OrderItemState.PREPARING));
	}

	public ServiceResult<OrderItemResponse> updateFromMenu(OrderItemRequest request) {
		try {
			OrderItem orderItem = repository.getOne(request.getId());
			if (!OrderItemState.WAITING_FOR_TABLE.equals(orderItem.getState())) {
				return forbidden();
			}
			updateInternal(orderItem, request);
			return new ServiceResult<>(mapper.toResponse(orderItem), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<OrderItemResponse> update(String token, OrderItemRequest request) {
		try {
			User user = getUser(token);
			Order order = orderRepository.getOne(request.getOrderId());
			if (!isNotAdmin(user) && !order.getBrand().getId().equals(user.getBrand().getId())) {
				return forbidden();
			}
			OrderItem orderItem = order.getOrderItems().stream().filter(orderItem1 -> orderItem1.getId().equals(request.getId())).findFirst().orElse(null);
			if (orderItem != null) {
				updateInternal(orderItem, request);
				orderUtils.updateTotalPrice(order);
				orderRepository.save(order);
				return new ServiceResult<>(mapper.toResponse(orderItem), HttpStatus.OK);
			} else {
				return new ServiceResult<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public void updateInternal(OrderItem orderItem, OrderItemRequest request) {
		BigDecimal oldSubTotal = orderItem.getTotalPrice();
			orderItem.setComment(request.getComment());
			orderItem.setAmount(request.getAmount());
			orderItem.setPortion(request.getPortion());
			orderItem.setTotalPrice(orderItem.getProduct().getPrice()
					.multiply(BigDecimal.valueOf(orderItem.getPortion()))
					.multiply(BigDecimal.valueOf(orderItem.getAmount())));
			repository.save(orderItem);
			BigDecimal newSubTotal = orderItem.getTotalPrice();
			Order order = orderItem.getOrder();
			order.setTotalPrice(order.getTotalPrice().add(newSubTotal.subtract(oldSubTotal)));
			orderRepository.save(order);
	}

	public ServiceResult<Boolean> delete(String token, Long id) {
		try {
			User user = getUser(token);
			OrderItem entity = repository.getOne(id);
			Order order = entity.getOrder();
			if (!isNotAdmin(user) && !order.getBrand().getId().equals(user.getBrand().getId())) {
				return forbiddenBoolean();
			}
			repository.delete(entity);
			return new ServiceResult<>(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<Boolean> cancelFromAdminPanel(String token, OrderItemCancelRequest request) {
		try {
			User user = getUser(token);
			OrderItem entity = repository.getOne(request.getId());
			Order order = entity.getOrder();
			if (!OrderItemState.WAITING_FOR_TABLE.equals(entity.getState()) && isNotAdmin(user) && isManager(user) && !user.getBrand().getId().equals(order.getBrand().getId())) {
				return forbiddenBoolean();
			}
			return cancel(entity, request);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<Boolean> cancelFromMenu(OrderItemCancelRequest request) {
		try {
			OrderItem entity = repository.getOne(request.getId());
			if (!OrderItemState.WAITING_FOR_TABLE.equals(entity.getState())) {
				return forbiddenBoolean();
			}
			return cancel(entity, request);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<Boolean> cancel(OrderItem entity, OrderItemCancelRequest request) {
		try {
			if (entity.getAmount() - request.getCancelAmount() < 0) {
				return new ServiceResult<>(false, HttpStatus.BAD_REQUEST);
			}

			if (entity.getAmount() - request.getCancelAmount() == 0) {
				entity.setState(OrderItemState.CANCELLED);
			}

			entity.setAmount(entity.getAmount() - request.getCancelAmount());
			if (StringUtils.isNotEmpty(entity.getCancelReason())) {
				entity.setCancelReason(entity.getCancelReason() + ", " + request.getCancelReason());
			} else {
				entity.setCancelReason(request.getCancelReason());
			}
			OrderItem savedEntity =  repository.save(entity);

			//TODO masa kapatildiginda order ve order itemler posife tasinacak

			Order order = savedEntity.getOrder();
			order.setTotalPrice(order.getTotalPrice().subtract(entity.getProduct().getPrice().multiply(BigDecimal.valueOf(request.getCancelAmount()))));
			orderRepository.save(order);

			if (isAllCancelled(order.getOrderItems())) {
				RTable table = order.getTable();
				table.setIsOpen(false);
				tableRepository.save(table);
			}

			return new ServiceResult<>(true, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	private boolean isAllCancelled(List<OrderItem> orderItems) {
		for (OrderItem orderItem : orderItems) {
			if (!OrderItemState.CANCELLED.equals(orderItem.getState())) {
				return false;
			}
		}
		return true;
	}
}
