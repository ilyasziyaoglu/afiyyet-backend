package com.smartmenu.brand.service;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.brand.db.repository.BrandRepository;
import com.smartmenu.brand.mapper.BrandMapper;
import com.smartmenu.brand.mapper.BrandUpdateMapper;
import com.smartmenu.client.brand.BrandRequest;
import com.smartmenu.client.brand.BrandResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class BrandService extends AbstractBaseService<BrandRequest, Brand, BrandResponse, BrandMapper> {
	final private BrandRepository repository;
	final private BrandMapper mapper;
	final private BrandUpdateMapper updateMapper;

	@Override
	public BrandRepository getRepository() {
		return repository;
	}

	@Override
	public BrandMapper getMapper() {
		return mapper;
	}

	@Override
	public BrandUpdateMapper getUpdateMapper() {
		return updateMapper;
	}

}
