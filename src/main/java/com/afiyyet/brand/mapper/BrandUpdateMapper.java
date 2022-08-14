package com.afiyyet.brand.mapper;

import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.client.brand.BrandRequest;
import com.afiyyet.common.basemodel.mapper.BaseUpdateMapper;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
public class BrandUpdateMapper implements BaseUpdateMapper<BrandRequest, Brand> {

	@Override
	public Brand toEntityForUpdate(BrandRequest request, Brand entity) {

		if (entity == null) {
			entity = new Brand();
		}

		if (request.getName() != null) {
			entity.setName(request.getName());
		}
		if (request.getUniqueName() != null) {
			entity.setUniqueName(request.getUniqueName());
		}
		if ( request.getStatus() != null ) {
			entity.setStatus(request.getStatus());
		}
		if (request.getLogoImgUrl() != null ) {
			entity.setLogoImgUrl(request.getLogoImgUrl());
		}

		return entity;
	}
}
