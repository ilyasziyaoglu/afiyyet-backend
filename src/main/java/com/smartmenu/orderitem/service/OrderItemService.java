package com.smartmenu.orderitem.service;

import com.smartmenu.client.orderitem.OrderItemRequest;
import com.smartmenu.client.orderitem.OrderItemResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.user.db.entity.User;
import com.smartmenu.orderitem.db.entity.OrderItem;
import com.smartmenu.orderitem.db.repository.OrderItemRepository;
import com.smartmenu.orderitem.mapper.OrderItemMapper;
import com.smartmenu.orderitem.mapper.OrderItemUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
			Optional<OrderItem> entity = getRepository().findById(request.getId());
			if (entity.isPresent()) {
				if (!isNotAdmin(user) && !entity.get().getOrder().getBrand().getId().equals(user.getBrand().getId())) {
					return forbidden();
				}
				OrderItem newEntity = getUpdateMapper().toEntityForUpdate(request, entity.get());
				return new ServiceResult<>(repository.save(newEntity), HttpStatus.OK);
			} else {
				return new ServiceResult<>(HttpStatus.NOT_FOUND, "Entity not found to update update with the given id: " + request.getId());
			}
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
}
