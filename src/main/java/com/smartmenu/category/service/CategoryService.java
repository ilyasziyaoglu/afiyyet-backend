package com.smartmenu.category.service;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.brand.db.repository.BrandRepository;
import com.smartmenu.category.db.entity.Category;
import com.smartmenu.category.db.repository.CategoryRepository;
import com.smartmenu.category.mapper.CategoryMapper;
import com.smartmenu.category.mapper.CategoryUpdateMapper;
import com.smartmenu.client.category.ArrangeCategoryRequest;
import com.smartmenu.client.category.CategoryRequest;
import com.smartmenu.client.category.CategoryResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.user.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class CategoryService extends AbstractBaseService<CategoryRequest, Category, CategoryResponse, CategoryMapper> {
	final private CategoryRepository repository;
	final private CategoryMapper mapper;
	final private CategoryUpdateMapper updateMapper;
	final private BrandRepository brandRepository;

	@Override
	public CategoryRepository getRepository() {
		return repository;
	}

	@Override
	public CategoryMapper getMapper() {
		return mapper;
	}

	@Override
	public CategoryUpdateMapper getUpdateMapper() {
		return updateMapper;
	}

	@Override
	public ServiceResult<Category> save(String token, CategoryRequest request) {
		ServiceResult<Category> serviceResult = new ServiceResult<>();
		try {
			Category entityToSave = getMapper().toEntity(request);
			Brand brand = brandRepository.getOne(getUser(token).getBrand().getId());
			entityToSave.setBrand(brand);
			Category entity = getRepository().save(entityToSave);
			serviceResult.setValue(entity);
			serviceResult.setHttpStatus(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setMessage("Entity can not save. Error message: " + e.getMessage());
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return serviceResult;
	}

	public ServiceResult<Boolean> arrangeCategories(String token, List<ArrangeCategoryRequest> dto) {
		ServiceResult<Boolean> serviceResult = new ServiceResult<>();

		User user = getUser(token);

		Set<Long> ids = dto.stream().map(ArrangeCategoryRequest::getId).collect(Collectors.toSet());
		try {
			Optional<List<Category>> entityList = Optional.of(getRepository().findAllById(ids));
			int index = 0;
			for (Category category : entityList.get()) {
				if (user.getBrand().equals(category.getBrand())) {
					category.setOrder(dto.get(index++).getOrder());
					getRepository().save(category);
				}
			}
			serviceResult.setValue(true);
			serviceResult.setHttpStatus(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			serviceResult.setMessage(e.getMessage());
		}
		return serviceResult;
	}

	public ServiceResult<List<Category>> getCategoriesByBrand(String token) {
		ServiceResult<List<Category>> serviceResult = new ServiceResult<>();
		try {
			List<Category> entityList = getRepository().findAllByBrand(getUser(token).getBrand());
			entityList = entityList.stream().sorted(Comparator.comparing(Category::getOrder)).collect(Collectors.toList());
			serviceResult.setValue(entityList);
			serviceResult.setHttpStatus(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			serviceResult.setMessage(e.getMessage());
		}
		return serviceResult;
	}
}
