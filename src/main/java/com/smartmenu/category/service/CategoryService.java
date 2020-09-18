package com.smartmenu.category.service;

import com.smartmenu.category.db.entity.Category;
import com.smartmenu.category.db.repository.CategoryRepository;
import com.smartmenu.category.mapper.CategoryMapper;
import com.smartmenu.category.mapper.CategoryUpdateMapper;
import com.smartmenu.client.category.ArrangeCategoryRequest;
import com.smartmenu.client.category.CategoryRequest;
import com.smartmenu.client.category.CategoryResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.product.db.entity.Product;
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

	public ServiceResult<Boolean> arrangeCategories(String token, ArrangeCategoryRequest dto) {
		ServiceResult<Boolean> serviceResult = new ServiceResult<>();
		Set<Long> ids = dto.getArrangement().keySet();
		try {
			Optional<List<Category>> entityList = Optional.of(getRepository().findAllById(ids));
			entityList.get().forEach(category -> {
				category.setOrder(dto.getArrangement().get(category.getId()));
				getRepository().save(category);
			});
			serviceResult.setValue(true);
			serviceResult.setHttpStatus(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			serviceResult.setMessage(e.getMessage());
		}
		return serviceResult;
	}
}
