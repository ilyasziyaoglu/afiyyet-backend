package com.smartmenu.product.controller;

import com.smartmenu.client.product.ArrangeProductRequest;
import com.smartmenu.client.product.ProductRequest;
import com.smartmenu.client.product.ProductResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.product.db.entity.Product;
import com.smartmenu.product.mapper.ProductMapper;
import com.smartmenu.product.service.ProductService;
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
public class ProductController extends AbstractBaseController<ProductRequest, Product, ProductResponse, ProductMapper,
		ProductService> {
	private ProductService service;
	private ProductMapper mapper;

	public ProductController(final ProductService service, final ProductMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	public ProductService getService() {
		return service;
	}

	public ProductMapper getMapper() {
		return mapper;
	}

	@GetMapping("/get-products-by-category/{categoryId}")
	public ResponseEntity<List<ProductResponse>> getProductsByCategory(@RequestHeader(HEADER_TOKEN) String token, @Valid @PathVariable Long categoryId) {
		ServiceResult<List<Product>> serviceResult = getService().getProductsByCategory(token, categoryId);
		return new ResponseEntity<>(getMapper().toResponse(serviceResult.getValue()), serviceResult.getHttpStatus());
	}

	@PostMapping("/arrange-products")
	public ResponseEntity<Boolean> arrangeProducts(@RequestHeader(HEADER_TOKEN) String token, @RequestBody Map<Long, Integer> dto) {
		ServiceResult<Boolean> serviceResult = getService().arrangeProducts(token, dto);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}
}
