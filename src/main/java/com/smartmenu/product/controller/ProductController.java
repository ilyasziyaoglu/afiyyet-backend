package com.smartmenu.product.controller;

import com.smartmenu.client.product.ProductRequest;
import com.smartmenu.client.product.ProductResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.product.db.entity.Product;
import com.smartmenu.product.mapper.ProductMapper;
import com.smartmenu.product.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
