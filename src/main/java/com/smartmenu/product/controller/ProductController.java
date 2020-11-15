package com.smartmenu.product.controller;

import com.smartmenu.client.product.BulkPriceUpdateRequest;
import com.smartmenu.client.product.ProductRequest;
import com.smartmenu.client.product.ProductResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.product.service.ProductService;
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
@RequestMapping(value = "/product")
public class ProductController extends AbstractBaseController {
	private ProductService service;

	public ProductController(final ProductService service) {
		this.service = service;
	}

	public ProductService getService() {
		return service;
	}

	@GetMapping(GUEST + "/{id}")
	public ResponseEntity<ServiceResult<ProductResponse>> get(@PathVariable Long id) {
		ServiceResult<ProductResponse> serviceResult = service.get(id);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ServiceResult<ProductResponse>> save(@RequestHeader(HEADER_TOKEN) String token, @RequestBody ProductRequest request) {
		ServiceResult<ProductResponse> serviceResult = service.insert(token, request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<ServiceResult<ProductResponse>> update(@RequestHeader(HEADER_TOKEN) String token, @RequestBody ProductRequest request) {
		ServiceResult<ProductResponse> serviceResult = service.update(token, request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ServiceResult<Boolean>> delete(@RequestHeader(HEADER_TOKEN) String token, @Valid @PathVariable Long id) {
		ServiceResult<Boolean> serviceResult = service.delete(token, id);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@GetMapping("/get-products-by-category/{categoryId}")
	public ResponseEntity<ServiceResult<List<ProductResponse>>> getProductsByCategory(@RequestHeader(HEADER_TOKEN) String token, @Valid @PathVariable Long categoryId) {
		ServiceResult<List<ProductResponse>> serviceResult = getService().getProductsByCategory(token, categoryId);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping("/arrange-products")
	public ResponseEntity<ServiceResult<Boolean>> arrangeProducts(@RequestHeader(HEADER_TOKEN) String token, @RequestBody Map<Long, Integer> dto) {
		ServiceResult<Boolean> serviceResult = getService().arrangeProducts(token, dto);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping("/bulk-price-update")
	public ResponseEntity<ServiceResult<Boolean>> arrangeProducts(@RequestHeader(HEADER_TOKEN) String token, @RequestBody BulkPriceUpdateRequest dto) {
		ServiceResult<Boolean> serviceResult = getService().bulkPriceUpdate(token, dto);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}
}
