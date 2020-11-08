package com.smartmenu.rtable.service;

import com.smartmenu.client.rtable.RTableRequest;
import com.smartmenu.client.rtable.RTableResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.user.db.entity.User;
import com.smartmenu.rtable.db.entity.RTable;
import com.smartmenu.rtable.db.repository.RTableRepository;
import com.smartmenu.rtable.mapper.RTableMapper;
import com.smartmenu.rtable.mapper.RTableUpdateMapper;
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
public class RTableService extends AbstractBaseService<RTableRequest, RTable, RTableResponse, RTableMapper> {
	final private RTableRepository repository;
	final private RTableMapper mapper;
	final private RTableUpdateMapper updateMapper;

	@Override
	public RTableRepository getRepository() {
		return repository;
	}

	@Override
	public RTableMapper getMapper() {
		return mapper;
	}

	@Override
	public RTableUpdateMapper getUpdateMapper() {
		return updateMapper;
	}

	@Override
	public ServiceResult<RTable> save(String token, RTableRequest request) {
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
	public ServiceResult<RTable> update(String token, RTableRequest request) {
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
}
