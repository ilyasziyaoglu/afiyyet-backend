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
			entityToSave.setState(OrderItemState.WAITING_FOR_TABLE);
			OrderItem entity = repository.save(entityToSave);
			return new ServiceResult<>(mapper.toResponse(entity), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<OrderItemResponse> updateFromMenu(OrderItemRequest request) {
		try {
			OrderItem entity = repository.getOne(request.getId());
			if (!OrderItemState.WAITING_FOR_TABLE.equals(entity.getState())) {
				return forbidden();
			}
			return updateInternal(entity, request);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<OrderItemResponse> update(String token, OrderItemRequest request) {
		try {
			User user = getUser(token);
			OrderItem entity = repository.getOne(request.getId());
			if (!isNotAdmin(user) && !entity.getOrder().getBrand().getId().equals(user.getBrand().getId())) {
				return forbidden();
			}
			return updateInternal(entity, request);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<OrderItemResponse> updateInternal(OrderItem orderItem, OrderItemRequest request) {
		try {
			orderItem.setComment(request.getComment());
			orderItem.setAmount(request.getAmount());
			orderItem.setPortion(request.getPortion());
			return new ServiceResult<>(mapper.toResponse(repository.save(orderItem)), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<Boolean> delete(String token, Long id) {
		try {
			User user = getUser(token);
			OrderItem entity = repository.getOne(id);
			if (!isNotAdmin(user) && !entity.getOrder().getBrand().getId().equals(user.getBrand().getId())) {
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

			if (!OrderItemState.WAITING_FOR_TABLE.equals(entity.getState()) && isNotAdmin(user) && isManager(user) && !user.getBrand().getId().equals(entity.getOrder().getId())) {
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
