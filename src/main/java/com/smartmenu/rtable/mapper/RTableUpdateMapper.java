package com.smartmenu.rtable.mapper;

import com.smartmenu.brand.mapper.BrandMapper;
import com.smartmenu.client.rtable.RTableRequest;
import com.smartmenu.common.basemodel.mapper.BaseUpdateMapper;
import com.smartmenu.rtable.db.entity.RTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
@RequiredArgsConstructor
public class RTableUpdateMapper implements BaseUpdateMapper<RTableRequest, RTable> {

	final private BrandMapper brandMapper;

	@Override
	public RTable toEntityForUpdate(RTableRequest request, RTable entity) {
		if (entity == null) {
			entity = new RTable();
		}

		if (request.getBrand() != null) {
			entity.setBrand(brandMapper.toEntity(request.getBrand()));
		}

		return entity;
	}
}