package com.smartmenu.orderitem.service;

import com.smartmenu.client.orderitem.OrderItemCancelRequest;
import com.smartmenu.client.orderitem.OrderItemRequest;
import com.smartmenu.client.orderitem.OrderItemResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.user.db.entity.User;
import com.smartmenu.orderitem.db.entity.OrderItem;
import com.smartmenu.orderitem.db.repository.OrderItemRepository;
import com.smartmenu.orderitem.enums.OrderItemState;
import com.smartmenu.orderitem.mapper.OrderItemMapper;
import com.smartmenu.orderitem.mapper.OrderItemUpdateMapper;
import com.smartmenu.rtable.db.entity.RTable;
import com.smartmenu.rtable.db.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class OrderItemService extends AbstractBaseService<OrderItemRequest, OrderItem, OrderItemResponse, OrderItemMapper> {
	final private OrderItemRepository repository;
	final private OrderItemMapper mapper;
	final private OrderItemUpdateMapper updateMapper;
	final private TableRepository tableRepository;

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

	public ServiceResult<OrderItem> save(OrderItemRequest request) {
		try {
			OrderItem entityToSave = getMapper().toEntity(request);
			OrderItem entity = repository.save(entityToSave);
			return new ServiceResult<>(entity, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Entity can not save. Error message: " + e.getMessage());
		}
	}

	@Override
	public ServiceResult<OrderItem> update(String token, OrderItemRequest request) {
		try {
			User user = getUser(token);
			OrderItem entity = repository.getOne(request.getId());
			if (!OrderItemState.WAITING_FOR_TABLE.equals(entity.getState()) && !isNotAdmin(user) && !entity.getOrder().getBrand().getId().equals(user.getBrand().getId())) {
				return forbidden();
			}

			entity.setComment(request.getComment());
			entity.setAmount(request.getAmount());
			entity.setPortion(request.getPortion());
			return new ServiceResult<>(repository.save(entity), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.NOT_MODIFIED, "Entity can not update with the given id: " + request.getId() + ". Error message: " + e.getMessage());
		}
	}

	@Override
	public ServiceResult<Boolean> delete(String token, Long id) {
		User user = getUser(token);
		OrderItem entity = repository.getOne(id);
		if (!isNotAdmin(user) && !entity.getOrder().getBrand().getId().equals(user.getBrand().getId())) {
			return forbiddenBoolean();
		}
		return super.delete(token, id);
	}

	@Override
	public ServiceResult<Boolean> deleteAll(String token, Set<Long> ids) {
		User user = getUser(token);
		List<OrderItem> entityList = repository.findAllById(ids);
		for (OrderItem entity : entityList) {
			if (isNotAdmin(user) && !entity.getOrder().getBrand().getId().equals(user.getBrand().getId())) {
				return forbiddenBoolean();
			}
		}
		return super.deleteAll(token, ids);
	}

	public ServiceResult<Boolean> cancelFromAdminPanel(String token, OrderItemCancelRequest request) {
		try {
			User user = getUser(token);
			OrderItem entity = repository.getOne(request.getId());

			if (!OrderItemState.WAITING_FOR_TABLE.equals(entity.getState()) && isNotAdmin(user) && isManager(user) && !user.getBrand().getId().equals(entity.getOrder().getId())) {
				return forbiddenBoolean();
			}
			return cancel(entity, request);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.NOT_MODIFIED, "Entity can not cancel with the given id: " + request.getId() + ". Error message: " + e.getMessage());
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
			return new ServiceResult<>(HttpStatus.NOT_MODIFIED, "Entity can not cancel with the given id: " + request.getId() + ". Error message: " + e.getMessage());
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
			if (!entity.getCancelReason().isEmpty()) {
				entity.setCancelReason(entity.getCancelReason() + ", " + request.getCancelReason());
			} else {
				entity.setCancelReason(request.getCancelReason());
			}
			OrderItem savedEntity =  repository.save(entity);

			//TODO masa kapatildiginda order ve order itemler posife tasinacak
			if (isAllCancelled(savedEntity.getOrder().getOrderitems())) {
				RTable table = savedEntity.getOrder().getTable();
				table.setIsOpen(false);
				tableRepository.save(table);
			}

			return new ServiceResult<>(true, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.NOT_MODIFIED, "Entity can not cancel with the given id: " + request.getId() + ". Error message: " + e.getMessage());
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
