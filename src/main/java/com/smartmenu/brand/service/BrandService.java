package com.smartmenu.brand.service;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.brand.db.repository.BrandRepository;
import com.smartmenu.brand.mapper.BrandMapper;
import com.smartmenu.brand.mapper.BrandUpdateMapper;
import com.smartmenu.category.db.entity.Category;
import com.smartmenu.category.db.repository.CategoryRepository;
import com.smartmenu.client.brand.BrandRequest;
import com.smartmenu.client.brand.BrandResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;

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

	@Override
	public ServiceResult<Brand> save(String token, BrandRequest request) {
		try {
			if (isNotAdmin(token)) {
				return forbidden();
			}
			Brand entity = getRepository().save(mapper.toEntity(request));
			categoryRepository.save(new Category("MENÜLER", -1, entity.getId()));
			categoryRepository.save(new Category("KAMPANYALAR", -2, entity.getId()));
			return new ServiceResult<>(entity, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Entity can not save. Error message: " + e.getMessage());
		}
	}

	@Override
	public ServiceResult<Brand> update(String token, @NotNull BrandRequest request) {
		try {
			if (isNotAdmin(token)) {
				return forbidden();
			}
			Optional<Brand> entity = getRepository().findById(request.getId());
			if (entity.isPresent()) {
				Brand newEntity = getUpdateMapper().toEntityForUpdate(request, entity.get());
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
		if (!isNotAdmin(token)) {
			return forbiddenBoolean();
		}
		return super.delete(token, id);
	}

	@Override
	public ServiceResult<Boolean> deleteAll(String token, Set<Long> ids) {
		if (isNotAdmin(token)) {
			return forbiddenBoolean();
		}
		return super.deleteAll(token, ids);
	}

}
