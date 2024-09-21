package com.afiyyet.rtable.mapper;

import com.afiyyet.brand.mapper.BrandMapper;
import com.afiyyet.client.rtable.TableRequest;
import com.afiyyet.common.basemodel.mapper.BaseUpdateMapper;
import com.afiyyet.rtable.db.entity.RTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
@RequiredArgsConstructor
public class TableUpdateMapper implements BaseUpdateMapper<TableRequest, RTable> {

	private final BrandMapper brandMapper;

	@Override
	public RTable toEntityForUpdate(TableRequest request, RTable entity) {
		if (entity == null) {
			entity = new RTable();
		}

		if (request.getBrand() != null) {
			entity.setBrand(brandMapper.toEntity(request.getBrand()));
		}

		return entity;
	}
}
