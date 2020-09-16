package com.smartmenu.category.controller;

import com.smartmenu.category.db.entity.Category;
import com.smartmenu.category.mapper.CategoryMapper;
import com.smartmenu.category.service.CategoryService;
import com.smartmenu.client.category.CategoryRequest;
import com.smartmenu.client.category.CategoryResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/category")
public class CategoryController extends AbstractBaseController<CategoryRequest, Category, CategoryResponse, CategoryMapper, CategoryService> {
	private CategoryService service;
	private CategoryMapper mapper;

	public CategoryController(final CategoryService service, final CategoryMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	public CategoryService getService() {
		return service;
	}

	public CategoryMapper getMapper() {
		return mapper;
	}
}
