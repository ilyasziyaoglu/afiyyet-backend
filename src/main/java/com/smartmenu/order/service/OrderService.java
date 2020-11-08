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
import com.smartmenu.orderitem.service.OrderItemService;
import com.smartmenu.rtable.db.entity.RTable;
import com.smartmenu.rtable.db.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	final private OrderItemService orderItemService;
	final private OrderItemRepository orderItemRepository;

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

	public ServiceResult<Order> save(OrderRequest request) {
		try {
			Order savedOrder;
			RTable table = tableRepository.getOne(request.getTable().getId());

			if (table.getIsOpen()) {
				Order orderInDb = repository.getOne(table.getOrder().getId());
				request.getOrderitems().forEach(orderItemRequest -> {
					ServiceResult<OrderItem> orderItemServiceResult = orderItemService.save(orderItemRequest);
					if (orderItemServiceResult.isSuccess()) {
						orderInDb.getOrderitems().add(orderItemServiceResult.getValue());
					}
				});
				savedOrder = repository.save(orderInDb);
			} else {
				savedOrder = repository.save(getMapper().toEntity(request));
			}

			table.setIsOpen(true);
			tableRepository.save(table);

			return new ServiceResult<>(savedOrder, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Entity can not save. Error message: " + e.getMessage());
		}
	}

	@Override
	public ServiceResult<Order> update(String token, OrderRequest request) {
		try {
			User user = getUser(token);
			Order entity = repository.getOne(request.getId());
			if (!isNotAdmin(user) && !entity.getBrand().getId().equals(user.getBrand().getId())) {
				return forbidden();
			}
			Order newEntity = getUpdateMapper().toEntityForUpdate(request, entity);
			return new ServiceResult<>(repository.save(newEntity), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.NOT_MODIFIED, "Entity can not update with the given id: " + request.getId() + ". Error message: " + e.getMessage());
		}
	}

	@Override
	public ServiceResult<Boolean> delete(String token, Long id) {
		User user = getUser(token);
		Order entity = repository.getOne(id);
		if (isNotAdmin(user) && !isManager(user) && !entity.getBrand().getId().equals(user.getBrand().getId())) {
			return forbiddenBoolean();
		}
		return super.delete(token, id);
	}

	@Override
	public ServiceResult<Boolean> deleteAll(String token, Set<Long> ids) {
		User user = getUser(token);
		List<Order> entityList = repository.findAllById(ids);
		for (Order entity : entityList) {
			if (isNotAdmin(user) && !isManager(user) && !entity.getBrand().getId().equals(user.getBrand().getId())) {
				return forbiddenBoolean();
			}
		}
		return super.deleteAll(token, ids);
	}

	public ServiceResult<Boolean> cancel(String token, Long id) {
		try {
			User user = getUser(token);
			Order order = repository.getOne(id);

			if (!isNotAdmin(user) && !isManager(user) && !order.getBrand().getId().equals(user.getBrand().getId())) {
				return forbiddenBoolean();
			}

			Iterator<OrderItem> iterator = order.getOrderitems().iterator();
			while (iterator.hasNext()) {
				OrderItem orderItem = iterator.next();
				orderItem.setState(OrderItemState.CANCELLED);
				orderItem.setCancelReason("Yönetim tarafından tüm sipariş iptal edildi!");
				orderItemRepository.save(orderItem);
				iterator.remove();
			}
			repository.save(order);

			//TODO masa kapatildiginda order ve order itemler posife tasinacak
			if (order.getOrderitems().size() == 0) {
				RTable table = order.getTable();
				table.setIsOpen(false);
				tableRepository.save(table);
			}

			return new ServiceResult<>(true, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.NOT_MODIFIED, "Entity can not cancell with the given id: " + id + ". Error message: " + e.getMessage());
		}
	}
}
