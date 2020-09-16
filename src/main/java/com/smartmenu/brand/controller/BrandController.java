package com.smartmenu.brand.controller;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.brand.mapper.BrandMapper;
import com.smartmenu.brand.service.BrandService;
import com.smartmenu.client.brand.BrandRequest;
import com.smartmenu.client.brand.BrandResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/brand")
public class BrandController extends AbstractBaseController<BrandRequest, Brand,
		BrandResponse, BrandMapper, BrandService> {
	private BrandService service;
	private BrandMapper mapper;

	public BrandController(final BrandService service, final BrandMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	public BrandService getService() {
		return service;
	}

	public BrandMapper getMapper() {
		return mapper;
	}
}
