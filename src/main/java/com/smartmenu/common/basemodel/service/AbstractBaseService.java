package com.smartmenu.common.basemodel.service;

import com.smartmenu.common.basemodel.db.entity.AbstractBaseEntity;
import com.smartmenu.common.basemodel.db.repository.BaseRepository;
import com.smartmenu.common.basemodel.mapper.BaseMapper;
import com.smartmenu.common.basemodel.mapper.BaseUpdateMapper;
import com.smartmenu.common.basemodel.request.BaseRequest;
import com.smartmenu.common.basemodel.request.pager.DtColumnDto;
import com.smartmenu.common.basemodel.request.pager.DtOrderDto;
import com.smartmenu.common.basemodel.request.pager.PageDto;
import com.smartmenu.common.basemodel.response.BaseResponse;
import com.smartmenu.common.user.db.entity.User;
import com.smartmenu.common.user.db.repository.UserRepository;
import com.smartmenu.common.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RequiredArgsConstructor
public abstract class AbstractBaseService<
		Request extends BaseRequest,
		Entity extends AbstractBaseEntity,
		Response extends BaseResponse,
		Mapper extends BaseMapper<Request, Entity, Response>> extends BaseService<Request, Entity, BaseRepository<Entity>, BaseUpdateMapper<Request, Entity>> {

	private JwtUtil jwtUtil;

	@Autowired
	private UserRepository userRepository;

	public abstract Mapper getMapper();

	public ServiceResult<Entity> get(String token, Long id) {
		Optional<Entity> entity = getRepository().findById(id);
		return entity.map(value -> new ServiceResult<>(value, HttpStatus.OK))
				.orElseGet(() -> new ServiceResult<>(HttpStatus.NOT_FOUND, "Entity can not found by the given id: " + id));
	}

	public ServiceResult<Boolean> delete(String token, Long id) {
		try {
			getRepository().deleteById(id);
			return new ServiceResult<>(true, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(false, HttpStatus.NOT_MODIFIED, "Entity can not delete by the given id: " + id + ". Error message: " + e.getMessage());
		}
	}

	public ServiceResult<Boolean> deleteAll(String token, Set<Long> ids) {
		try {
			ids.forEach(id -> getRepository().deleteById(id));
			return new ServiceResult<>(true, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(false, HttpStatus.NOT_MODIFIED, "Entities can not delete by the given ids: " + ids + ". Error message: " + e.getMessage());
		}
	}

	public ServiceResult<Entity> update(String token, @NotNull Request request) {
		Optional<Entity> entity = getRepository().findById(request.getId());
		if (entity.isPresent()) {
			try {
				Entity newEntity = getUpdateMapper().toEntityForUpdate(request, entity.get());
				getRepository().save(newEntity);
				return new ServiceResult<>(newEntity, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ServiceResult<>(HttpStatus.NOT_MODIFIED, "Entity can not update with the given id: " + request.getId() + ". Error message: " + e.getMessage());
			}
		} else {
			return new ServiceResult<>(HttpStatus.NOT_FOUND, "Entity not found to update update with the given id: " + request.getId());
		}
	}

	public ServiceResult<Entity> save(String token, Request request) {
		try {
			Entity entity = getRepository().save(getMapper().toEntity(request));
			return new ServiceResult<>(entity, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Entity can not save. Error message: " + e.getMessage());
		}
	}

	public ServiceResult<List<Entity>> getAll(String token) {
		try {
			Optional<List<Entity>> entityList = Optional.of(getRepository().findAll());
			return new ServiceResult<>(entityList.get(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	protected <T> ServiceResult<Page<Entity>> filter(Specification<Entity> specifications, PageDto<T> page) {
		try {
			PageRequest pageRequest = PageRequest.of(page.getPage(), page.getSize(), Sort.Direction.DESC, "id");
			if (!CollectionUtils.isEmpty(page.getOrder()) && !CollectionUtils.isEmpty(page.getColumns())) {
				Optional<DtOrderDto> dtOrderOptional = page.getOrder()
						.stream()
						.findFirst();
				if (dtOrderOptional.isPresent()) {
					DtOrderDto dtOrderDto = dtOrderOptional.get();
					DtColumnDto sortedColumn = page.getColumns()
							.stream()
							.filter((c) -> c.getName()
									.equals(dtOrderDto.getColumn()
											.toString()))
							.findFirst()
							.orElse(null);
					if (sortedColumn != null && sortedColumn.getOrderable()) {
						Sort.Order order = (new Sort.Order(Sort.Direction.fromString(dtOrderDto.getDir()), sortedColumn.getData())).ignoreCase();
						pageRequest = PageRequest.of(page.getPage(), page.getSize(), Sort.by(order));
					}
				}
			}

			Page<Entity> pageEntity = getRepository().findAll(specifications, pageRequest);
			return new ServiceResult<>(pageEntity, HttpStatus.OK);
		} catch (Exception var9) {
			System.out.println("filter got exception. Message = " + var9.getMessage());
			var9.printStackTrace();
			return new ServiceResult<>(HttpStatus.BAD_REQUEST, var9.getMessage());
//			logger.error("filter got exception. Message = " + var9.getMessage(), var9);
		}
	}

	public User getUser(String token) {
		try {
			if (!token.isEmpty() &&  !token.contains("null")) {
				String username = getJwtUtil().extractUsername(token);
				Optional<User> user = userRepository.findByUsername(username);
				if (user.isPresent()) {
					return user.get();
				}
			}
		} catch (Exception ignored) { }
		return null;
	}

	public JwtUtil getJwtUtil() {
		if (jwtUtil == null) {
			jwtUtil = new JwtUtil();
		}
		return jwtUtil;
	}
}
