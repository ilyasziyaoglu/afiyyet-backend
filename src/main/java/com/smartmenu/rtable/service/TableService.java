package com.smartmenu.rtable.service;

import com.smartmenu.client.rtable.TableCloseRequest;
import com.smartmenu.client.rtable.TableRequest;
import com.smartmenu.client.rtable.TableResponse;
import com.smartmenu.client.rtable.TableTransferRequest;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.user.db.entity.User;
import com.smartmenu.common.utils.PriceUtils;
import com.smartmenu.order.db.entity.Order;
import com.smartmenu.order.db.repository.OrderRepository;
import com.smartmenu.order.service.OrderService;
import com.smartmenu.orderitem.db.entity.OrderItem;
import com.smartmenu.orderitem.enums.OrderItemState;
import com.smartmenu.rtable.db.entity.RTable;
import com.smartmenu.rtable.db.repository.TableRepository;
import com.smartmenu.rtable.mapper.TableMapper;
import com.smartmenu.rtable.mapper.TableUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class TableService extends AbstractBaseService<TableRequest, RTable, TableResponse, TableMapper> {
	final private TableRepository repository;
	final private TableMapper mapper;
	final private TableUpdateMapper updateMapper;
	final private PriceUtils priceUtils;
	final private OrderRepository orderRepository;
	final private OrderService orderService;

	@Override
	public TableRepository getRepository() {
		return repository;
	}

	@Override
	public TableMapper getMapper() {
		return mapper;
	}

	@Override
	public TableUpdateMapper getUpdateMapper() {
		return updateMapper;
	}

	@Override
	public ServiceResult<RTable> get(String token, Long id) {
		try {
			User user = getUser(token);
			RTable entity = getRepository().getOne(id);
			if (isNotAdmin(user) && !user.getBrand().getId().equals(entity.getBrand().getId())) {
				return forbidden();
			}
			return new ServiceResult<>(entity, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@Override
	public ServiceResult<RTable> save(String token, TableRequest request) {
		try {
			RTable entityToSave = getMapper().toEntity(request);
			RTable entity = repository.save(entityToSave);
			return new ServiceResult<>(entity, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Entity can not save. Error message: " + e.getMessage());
		}
	}

	@Override
	public ServiceResult<RTable> update(String token, TableRequest request) {
		try {
			User user = getUser(token);
			Optional<RTable> entity = getRepository().findById(request.getId());
			if (entity.isPresent()) {
				if (!isNotAdmin(user) && !entity.get().getBrand().getId().equals(user.getBrand().getId())) {
					return forbidden();
				}
				RTable newEntity = getUpdateMapper().toEntityForUpdate(request, entity.get());
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
		RTable entity = repository.getOne(id);
		if (!isNotAdmin(user) && !entity.getBrand().getId().equals(user.getBrand().getId())) {
			return forbiddenBoolean();
		}
		return super.delete(token, id);
	}

	@Override
	public ServiceResult<Boolean> deleteAll(String token, Set<Long> ids) {
		User user = getUser(token);
		List<RTable> entityList = repository.findAllById(ids);
		for (RTable entity : entityList) {
			if (isNotAdmin(user) && !entity.getBrand().getId().equals(user.getBrand().getId())) {
				return forbiddenBoolean();
			}
		}
		return super.deleteAll(token, ids);
	}

	public ServiceResult<List<RTable>> getTablesByBrand(String token) {
		try {
			return new ServiceResult<>(repository.findAllByBrandId(getUser(token).getBrand().getId()), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Entity can not get tables. Error message: " + e.getMessage());
		}
	}

	public ServiceResult<Boolean> close(String token, TableCloseRequest request) {
		try {
			User user = getUser(token);
			RTable entity = repository.getOne(request.getId());
			if (isNotAdmin(user) && !entity.getBrand().getId().equals(user.getBrand().getId())) {
				return forbiddenBoolean();
			}

			BigDecimal totalPrice = new BigDecimal(0);
			for (OrderItem orderItem : entity.getOrder().getOrderitems()) {
				orderItem.setState(OrderItemState.COMPLETED);
				totalPrice = totalPrice.add(orderItem.getTotalPrice());
			}
			entity.setIsOpen(false);
			entity.getOrder().setTotalPrice(priceUtils.applyDiscount(totalPrice, request.getDiscountAmount(), request.getIsPercent()));
			repository.save(entity);
			return new ServiceResult<>(true, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	public ServiceResult<Boolean> transfer(String token, TableTransferRequest request) {
		try {
			User user = getUser(token);
			RTable source = repository.getOne(request.getSourceId());
			RTable target = repository.getOne(request.getTargetId());
			if (isNotAdmin(user) && !source.getBrand().getId().equals(user.getBrand().getId())) {
				return forbiddenBoolean();
			}

			Order sourceOrder = source.getOrder();
			source.setIsOpen(false);
			target.setIsOpen(true);
			if (target.getOrder() == null) {
				sourceOrder.setTable(target);
				source.setOrder(null);
				target.setOrder(sourceOrder);
				repository.save(source);
			} else {
				Order targetOrder = target.getOrder();
				List<OrderItem> transferItems = new ArrayList<>();
				sourceOrder.getOrderitems().forEach(orderItem -> {
					orderItem.setOrder(targetOrder);
					transferItems.add(orderItem);
				});
				targetOrder.getOrderitems().addAll(transferItems);
				orderService.cancel(token, sourceOrder.getId());
			}

			repository.save(target);

			return new ServiceResult<>(true, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
