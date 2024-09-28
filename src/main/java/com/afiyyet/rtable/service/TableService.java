package com.afiyyet.rtable.service;

import com.afiyyet.client.rtable.TableCloseRequest;
import com.afiyyet.client.rtable.TableRequest;
import com.afiyyet.client.rtable.TableResponse;
import com.afiyyet.client.rtable.TableTransferRequest;
import com.afiyyet.common.basemodel.service.AbstractBaseService;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.common.user.db.entity.User;
import com.afiyyet.common.utils.PriceUtils;
import com.afiyyet.order.db.entity.Order;
import com.afiyyet.order.db.repository.OrderRepository;
import com.afiyyet.order.service.OrderService;
import com.afiyyet.order.utils.OrderUtils;
import com.afiyyet.orderitem.db.entity.OrderItem;
import com.afiyyet.orderitem.enums.OrderItemState;
import com.afiyyet.rtable.db.entity.RTable;
import com.afiyyet.rtable.db.repository.TableRepository;
import com.afiyyet.rtable.mapper.TableMapper;
import com.afiyyet.rtable.mapper.TableUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class TableService extends AbstractBaseService<TableRequest, RTable, TableResponse, TableMapper> {
	private final TableRepository repository;
	private final TableMapper mapper;
	private final TableUpdateMapper updateMapper;
	private final PriceUtils priceUtils;
	private final OrderService orderService;
	private final OrderRepository orderRepository;
	private final OrderUtils orderUtils;

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
			entityToSave.setIsOpen(false);
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
			ServiceResult<List<TableResponse>> tables = new ServiceResult<>(mapper.toResponse(repository.findAllByBrandId(getUser(token).getBrand()
				.getId())));
			tables.getValue().sort(Comparator.comparing(TableResponse::getGroupName));
			return tables;
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
			RTable table = repository.getOne(request.getId());
			if (isNotAdmin(user) && !table.getBrand().getId().equals(user.getBrand().getId())) {
				return forbiddenBoolean();
			}

			BigDecimal totalPrice = new BigDecimal(0);
			for (OrderItem orderItem : table.getOrder().getOrderItems()) {
				orderItem.setState(OrderItemState.COMPLETED);
				totalPrice = totalPrice.add(orderItem.getTotalPrice());
			}
			table.setIsOpen(false);
			table.getOrder().setTotalPrice(priceUtils.applyDiscount(totalPrice, request.getDiscountAmount(), request.getPercent()));
			table.getOrder().setTable(null);
			orderRepository.save(table.getOrder());
			table.setOrder(null);
			repository.save(table);
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
				source.setOrder(null);
				sourceOrder.setTable(target);
				target.setOrder(sourceOrder);
				repository.save(source);
				repository.save(target);
			} else {
				sourceOrder.getOrderItems().forEach(orderItem -> {
					orderItem.setOrder(target.getOrder() );
					target.getOrder().getOrderItems().add(orderItem);
				});
				orderUtils.updateTotalPrice(target.getOrder());

				repository.save(target);
				source.setOrder(null);
				repository.save(source);

				sourceOrder.setTotalPrice(BigDecimal.ZERO);
				sourceOrder.setTable(null);
				orderRepository.save(sourceOrder);

			}
			return new ServiceResult<>(true, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}
}
