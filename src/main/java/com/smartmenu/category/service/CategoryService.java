package com.smartmenu.category.service;

import com.smartmenu.brand.db.model.FeatureType;
import com.smartmenu.category.db.entity.Category;
import com.smartmenu.category.db.repository.CategoryRepository;
import com.smartmenu.category.mapper.CategoryMapper;
import com.smartmenu.category.mapper.CategoryUpdateMapper;
import com.smartmenu.client.category.CategoryRequest;
import com.smartmenu.client.category.CategoryResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.user.db.entity.User;
import com.smartmenu.product.db.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
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
		User user = getUser(token);
		try {
			if (!user.getBrand().getFeatures().contains(FeatureType.CRUD_OPERATIONS)) {
				return forbidden();
			}
			Category entityToSave = getMapper().toEntity(request);
			entityToSave.setBrandId(user.getBrand().getId());
			Category entity = getRepository().save(entityToSave);
			return new ServiceResult<>(entity, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Entity can not save. Error message: " + e.getMessage());
		}
	}

	@Override
	public ServiceResult<Category> update(String token, CategoryRequest request) {
		try {
			User user = getUser(token);
			Category entity = repository.getOne(request.getId());
			if (!user.getBrand().getFeatures().contains(FeatureType.CRUD_OPERATIONS) || !user.getBrand().getId().equals(entity.getBrandId())) {
				return forbidden();
			}
			Category newEntity = updateMapper.toEntityForUpdate(request, entity);
			return new ServiceResult<>(repository.save(newEntity), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.NOT_MODIFIED, "Entity can not update with the given id: " + request.getId() + ". Error message: " + e.getMessage());
		}
	}

	@Override
	public ServiceResult<Boolean> delete(String token, Long id) {
		User user = getUser(token);
		Category entity = repository.getOne(id);
		if (!isNotAdmin(user) && !entity.getBrandId().equals(user.getBrand().getId())) {
			return forbiddenBoolean();
		}
		return super.delete(token, id);
	}

	@Override
	public ServiceResult<Boolean> deleteAll(String token, Set<Long> ids) {
		User user = getUser(token);
		List<Category> entityList = repository.findAllById(ids);
		for (Category entity : entityList) {
			if (isNotAdmin(user) && !entity.getBrandId().equals(user.getBrand().getId())) {
				return forbiddenBoolean();
			}
		}
		return super.deleteAll(token, ids);
	}

	public ServiceResult<Boolean> arrangeCategories(String token, Map<Long, Integer> dto) {
		User user = getUser(token);

		try {
			if (!user.getBrand().getFeatures().contains(FeatureType.ORDERING)) {
				return forbiddenBoolean();
			}
			Optional<List<Category>> entityList = Optional.of(getRepository().findAllById(dto.keySet()));
			for (Category category : entityList.get()) {
				if (user.getBrand().getId().equals(category.getBrandId())) {
					category.setOrder(dto.get(category.getId()));
					getRepository().save(category);
				}
			}return new ServiceResult<>(true, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	public ServiceResult<List<Category>> getCategoriesByBrand(String token) {
		try {
			List<Category> entityList = getRepository().findAllByBrandId(getUser(token).getBrand().getId());
			entityList = entityList.stream()
					.filter(category -> !KAMPANYALAR.equals(category.getName()) && !MENULER.equals(category.getName()))
					.sorted(Comparator.comparing(Category::getOrder, Comparator.nullsLast(Comparator.naturalOrder())))
					.collect(Collectors.toList());
			return new ServiceResult<>(entityList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	public ServiceResult<List<Product>> getCategoryByBrandAndName(String token, String name) {
		try {
			Category campaigns = repository.findAllByBrandIdAndName(getUser(token).getBrand().getId(), name);
			campaigns.getProducts().sort(Comparator.comparing(Product::getOrder, Comparator.nullsLast(Comparator.naturalOrder())));
			return new ServiceResult<>(campaigns.getProducts(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
