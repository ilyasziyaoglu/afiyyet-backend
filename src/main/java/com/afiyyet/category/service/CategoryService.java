package com.afiyyet.category.service;

import com.afiyyet.brand.db.model.FeatureType;
import com.afiyyet.category.db.entity.Category;
import com.afiyyet.category.db.repository.CategoryRepository;
import com.afiyyet.category.mapper.CategoryMapper;
import com.afiyyet.category.mapper.CategoryUpdateMapper;
import com.afiyyet.client.category.CategoryRequest;
import com.afiyyet.client.category.CategoryResponse;
import com.afiyyet.client.product.ProductResponse;
import com.afiyyet.common.basemodel.service.AbstractBaseService;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.common.user.db.entity.User;
import com.afiyyet.product.db.entity.Product;
import com.afiyyet.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
	final private ProductMapper productMapper;

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

	public ServiceResult<CategoryResponse> get(Long id) {
		try {
			return new ServiceResult<>(mapper.toResponse(repository.getOne(id)));
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<CategoryResponse> insert(String token, CategoryRequest request) {
		try {
			User user = getUser(token);
			if (!user.getBrand().getFeatures().contains(FeatureType.CRUD_OPERATIONS)) {
				return forbidden();
			}
			Category entityToSave = getMapper().toEntity(request);
			entityToSave.setBrandId(user.getBrand().getId());
			Category entity = getRepository().save(entityToSave);
			return new ServiceResult<>(mapper.toResponse(entity), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<CategoryResponse> update(String token, CategoryRequest request) {
		try {
			User user = getUser(token);
			Category entity = repository.getOne(request.getId());
			if (!user.getBrand().getFeatures().contains(FeatureType.CRUD_OPERATIONS) || !user.getBrand().getId().equals(entity.getBrandId())) {
				return forbidden();
			}
			Category newEntity = updateMapper.toEntityForUpdate(request, entity);
			return new ServiceResult<>(mapper.toResponse(repository.save(newEntity)));
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<Boolean> delete(String token, Long id) {
		try {
			User user = getUser(token);
			Category entity = repository.getOne(id);
			if (!isNotAdmin(user) && !entity.getBrandId().equals(user.getBrand().getId())) {
				return forbiddenBoolean();
			}
			repository.delete(entity);
			return new ServiceResult<>(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
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
			}return new ServiceResult<>(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<List<CategoryResponse>> getCategoriesByBrand(String token) {
		try {
			List<Category> entityList = getRepository().findAllByBrandId(getUser(token).getBrand().getId());
			entityList = entityList.stream()
					.filter(category -> !KAMPANYALAR.equals(category.getName()) && !MENULER.equals(category.getName()))
					.sorted(Comparator.comparing(Category::getOrder, Comparator.nullsLast(Comparator.naturalOrder())))
					.collect(Collectors.toList());
			return new ServiceResult<>(mapper.toResponse(entityList));
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<List<ProductResponse>> getCategoryByBrandAndName(String token, String name) {
		try {
			Category campaigns = repository.findAllByBrandIdAndName(getUser(token).getBrand().getId(), name);
			campaigns.getProducts().sort(Comparator.comparing(Product::getOrder, Comparator.nullsLast(Comparator.naturalOrder())));
			return new ServiceResult<>(productMapper.toResponse(campaigns.getProducts()));
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}
}
