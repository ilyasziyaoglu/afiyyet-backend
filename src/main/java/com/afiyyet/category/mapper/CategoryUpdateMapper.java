package com.afiyyet.category.mapper;

import com.afiyyet.brand.mapper.BrandMapper;
import com.afiyyet.category.db.entity.Category;
import com.afiyyet.client.category.CategoryRequest;
import com.afiyyet.common.basemodel.mapper.BaseUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
@RequiredArgsConstructor
public class CategoryUpdateMapper implements BaseUpdateMapper<CategoryRequest, Category> {

	private final BrandMapper brandMapper;

	@Override
	public Category toEntityForUpdate(CategoryRequest request, Category entity) {
		if (entity == null) {
			entity = new Category();
		}

		if (request.getName() != null) {
			entity.setName(request.getName());
		}
		if (request.getImgUrl() != null) {
			entity.setImgUrl(request.getImgUrl());
		}
		if (request.getOrder() != null) {
			entity.setOrder(request.getOrder());
		}
		if (request.getStatus() != null) {
			entity.setStatus(request.getStatus());
		}

		return entity;
	}
}
