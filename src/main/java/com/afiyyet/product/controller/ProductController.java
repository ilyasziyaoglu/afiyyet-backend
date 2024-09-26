package com.afiyyet.product.controller;

import com.afiyyet.client.product.BulkPriceUpdateRequest;
import com.afiyyet.client.product.ProductRequest;
import com.afiyyet.client.product.ProductResponse;
import com.afiyyet.common.basemodel.controller.AbstractBaseController;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/list")
	public ResponseEntity<ServiceResult<List<ProductResponse>>> getProductListByBrand(@RequestHeader(HEADER_TOKEN) String token) {
		ServiceResult<List<ProductResponse>> serviceResult = service.getProductListByBrand(token);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping("/arrange-products")
	public ResponseEntity<ServiceResult<Boolean>> arrangeProducts(@RequestHeader(HEADER_TOKEN) String token, @RequestBody Map<Long, Integer> dto) {
		ServiceResult<Boolean> serviceResult = service.arrangeProducts(token, dto);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping("/bulk-price-update")
	public ResponseEntity<ServiceResult<Boolean>> arrangeProducts(@RequestHeader(HEADER_TOKEN) String token, @RequestBody BulkPriceUpdateRequest dto) {
		ServiceResult<Boolean> serviceResult = service.bulkPriceUpdate(token, dto);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}
}
