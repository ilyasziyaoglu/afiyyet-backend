package com.afiyyet.category.controller;

import com.afiyyet.category.service.CategoryService;
import com.afiyyet.client.category.CategoryRequest;
import com.afiyyet.client.category.CategoryResponse;
import com.afiyyet.client.product.ProductResponse;
import com.afiyyet.common.basemodel.controller.AbstractBaseController;
import com.afiyyet.common.basemodel.service.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/category")
public class CategoryController extends AbstractBaseController {
	private CategoryService service;

	public CategoryController(final CategoryService service) {
		this.service = service;
	}

	public CategoryService getService() {
		return service;
	}

	@GetMapping(GUEST + "/{id}")
	public ResponseEntity<ServiceResult<CategoryResponse>> get(@PathVariable Long id) {
		ServiceResult<CategoryResponse> serviceResult = service.get(id);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ServiceResult<CategoryResponse>> save(@RequestHeader(HEADER_TOKEN) String token, @RequestBody CategoryRequest request) {
		ServiceResult<CategoryResponse> serviceResult = service.insert(token, request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<ServiceResult<CategoryResponse>> update(@RequestHeader(HEADER_TOKEN) String token, @RequestBody CategoryRequest request) {
		ServiceResult<CategoryResponse> serviceResult = service.update(token, request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ServiceResult<Boolean>> delete(@RequestHeader(HEADER_TOKEN) String token, @Valid @PathVariable Long id) {
		ServiceResult<Boolean> serviceResult = service.delete(token, id);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping("/arrange-categories")
	public ResponseEntity<ServiceResult<Boolean>> arrangeCategories(@RequestHeader(HEADER_TOKEN) String token, @RequestBody Map<Long, Integer> dto) {
		ServiceResult<Boolean> serviceResult = getService().arrangeCategories(token, dto);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@GetMapping("/get-categories-by-brand")
	public ResponseEntity<ServiceResult<List<CategoryResponse>>> getCategoriesByBrand(@RequestHeader(HEADER_TOKEN) String token) {
		ServiceResult<List<CategoryResponse>> serviceResult = service.getCategoriesByBrand(token);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@GetMapping("/get-campaigns-by-brand")
	public ResponseEntity<ServiceResult<List<ProductResponse>>> getCampaignsByBrand(@RequestHeader(HEADER_TOKEN) String token) {
		ServiceResult<List<ProductResponse>> serviceResult = service.getCategoryByBrandAndName(token, KAMPANYALAR);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@GetMapping("/get-menus-by-brand")
	public ResponseEntity<ServiceResult<List<ProductResponse>>> getMenusByBrand(@RequestHeader(HEADER_TOKEN) String token) {
		ServiceResult<List<ProductResponse>> serviceResult = service.getCategoryByBrandAndName(token, MENULER);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}
}
