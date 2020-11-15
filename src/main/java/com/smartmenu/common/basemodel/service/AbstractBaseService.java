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
import com.smartmenu.common.user.enums.UserRoles;
import com.smartmenu.common.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

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
	public String KAMPANYALAR = "KAMPANYALAR";
	public String MENULER = "MENÜLER";

	@Autowired
	private UserRepository userRepository;

	public abstract Mapper getMapper();

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

	public boolean isNotAdmin(String token) {
		return !getUser(token).getRoles().contains(UserRoles.ADMIN.name());
	}

	public boolean isNotAdmin(User user) {
		return !user.getRoles().contains(UserRoles.ADMIN.name());
	}

	public JwtUtil getJwtUtil() {
		if (jwtUtil == null) {
			jwtUtil = new JwtUtil();
		}
		return jwtUtil;
	}

	public ServiceResult<Response> forbidden() {
		return new ServiceResult<>(HttpStatus.FORBIDDEN, "Entity can not save. Error message: Required privilege not defined!");
	}

	public ServiceResult<List<Response>> forbiddenList() {
		return new ServiceResult<>(HttpStatus.FORBIDDEN, "Entity can not save. Error message: Required privilege not defined!");
	}

	public ServiceResult<Boolean> forbiddenBoolean() {
		return new ServiceResult<>(HttpStatus.FORBIDDEN, "Entity can not save. Error message: Required privilege not defined!");
	}
}
