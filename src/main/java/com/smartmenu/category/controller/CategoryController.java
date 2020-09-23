package com.smartmenu.category.controller;

import com.smartmenu.category.db.entity.Category;
import com.smartmenu.category.mapper.CategoryMapper;
import com.smartmenu.category.service.CategoryService;
import com.smartmenu.client.category.ArrangeCategoryRequest;
import com.smartmenu.client.category.CategoryRequest;
import com.smartmenu.client.category.CategoryResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.basemodel.service.ServiceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

	@PostMapping("/arrange-categories")
	public ResponseEntity<Boolean> arrangeCategories(@RequestHeader(HEADER_TOKEN) String token, @RequestBody Map<Long, Integer> dto) {
		ServiceResult<Boolean> serviceResult = getService().arrangeCategories(token, dto);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}

	@GetMapping("/get-categories-by-brand")
	public ResponseEntity<List<Category>> getCategoriesByBrand(@RequestHeader(HEADER_TOKEN) String token) {
		ServiceResult<List<Category>> serviceResult = getService().getCategoriesByBrand(token);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}
}
