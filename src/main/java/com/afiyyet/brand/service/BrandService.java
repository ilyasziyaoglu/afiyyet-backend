package com.afiyyet.brand.service;

import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.brand.db.repository.BrandRepository;
import com.afiyyet.brand.mapper.BrandMapper;
import com.afiyyet.brand.mapper.BrandUpdateMapper;
import com.afiyyet.category.db.entity.Category;
import com.afiyyet.category.db.repository.CategoryRepository;
import com.afiyyet.client.brand.BrandRequest;
import com.afiyyet.client.brand.BrandResponse;
import com.afiyyet.common.basemodel.service.AbstractBaseService;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.common.user.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class BrandService extends AbstractBaseService<BrandRequest, Brand, BrandResponse, BrandMapper> {
	final private BrandRepository repository;
	final private BrandMapper mapper;
	final private BrandUpdateMapper updateMapper;
	final private CategoryRepository categoryRepository;

	@Override
	public BrandRepository getRepository() {
		return repository;
	}

	@Override
	public BrandMapper getMapper() {
		return mapper;
	}

	@Override
	public BrandUpdateMapper getUpdateMapper() {
		return updateMapper;
	}

	public ServiceResult<BrandResponse> get(Long id) {
		try {
			return new ServiceResult<>(mapper.toResponse(repository.getOne(id)));
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<BrandResponse> insert(String token, BrandRequest request) {
		try {
			if (isNotAdmin(token)) {
				return forbidden();
			}
			Brand entity = getRepository().save(mapper.toEntity(request));
			categoryRepository.save(new Category(MENULER, -1, entity.getId()));
			categoryRepository.save(new Category(KAMPANYALAR, -2, entity.getId()));
			return new ServiceResult<>(mapper.toResponse(entity), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Entity can not save. Error message: " + e.getMessage());
		}
	}

	public ServiceResult<BrandResponse> update(String token, BrandRequest request) {
		try {
			if (isNotAdmin(token)) {
				return forbidden();
			}
			Optional<Brand> entity = getRepository().findById(request.getId());
			if (entity.isPresent()) {
				Brand newEntity = getUpdateMapper().toEntityForUpdate(request, entity.get());
				return new ServiceResult<>(mapper.toResponse(repository.save(newEntity)));
			} else {
				return new ServiceResult<>(HttpStatus.NOT_FOUND, "Entity not found to update update with the given id: " + request.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.NOT_MODIFIED,
					"Entity can not update with the given id: " + request.getId() + ". Error message: " + e.getMessage());
		}
	}

	public ServiceResult<Boolean> delete(String token, Long id) {
		try {
			User user = getUser(token);
			Brand entity = repository.getOne(id);
			if (!isNotAdmin(user)) {
				return forbiddenBoolean();
			}
			repository.delete(entity);
			return new ServiceResult<>(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}
}
