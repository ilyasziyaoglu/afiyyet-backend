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

	public ServiceResult<TableResponse> get(Long id) {
		try {
			return new ServiceResult<>(mapper.toResponse(repository.getOne(id)));
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<TableResponse> insert(String token, TableRequest request) {
		try {
			RTable entityToSave = getMapper().toEntity(request);
			entityToSave.setBrand(getUser(token).getBrand());
			RTable entity = repository.save(entityToSave);
			return new ServiceResult<>(mapper.toResponse(entity), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<TableResponse> update(String token, TableRequest request) {
		try {
			User user = getUser(token);
			Optional<RTable> entity = getRepository().findById(request.getId());
			if (entity.isPresent()) {
				if (!isNotAdmin(user) && !entity.get().getBrand().getId().equals(user.getBrand().getId())) {
					return forbidden();
				}
				RTable newEntity = getUpdateMapper().toEntityForUpdate(request, entity.get());
				return new ServiceResult<>(mapper.toResponse(repository.save(newEntity)));
			} else {
				return new ServiceResult<>(HttpStatus.NOT_FOUND, "Entity not found to update update with the given id: " + request.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<Boolean> delete(String token, Long id) {
		try {
			User user = getUser(token);
			RTable entity = repository.getOne(id);
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

	public ServiceResult<List<TableResponse>> getTablesByBrand(String token) {
		try {
			return new ServiceResult<>(mapper.toResponse(repository.findAllByBrandId(getUser(token).getBrand().getId())));
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<List<String>> getGroupNamesByBrand(String token) {
		try {
			return new ServiceResult<>(repository.findGroupNamesByBrandId(getUser(token).getBrand().getId()), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
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
			return new ServiceResult<>(e);
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
			return new ServiceResult<>(e);
		}
	}
}
