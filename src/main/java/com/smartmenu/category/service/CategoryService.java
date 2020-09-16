package com.smartmenu.category.service;

import com.smartmenu.category.db.entity.Category;
import com.smartmenu.category.db.repository.CategoryRepository;
import com.smartmenu.category.mapper.CategoryMapper;
import com.smartmenu.category.mapper.CategoryUpdateMapper;
import com.smartmenu.client.category.CategoryRequest;
import com.smartmenu.client.category.CategoryResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
