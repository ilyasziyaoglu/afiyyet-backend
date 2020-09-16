package com.smartmenu.category.mapper;

import com.smartmenu.category.db.entity.Category;
import com.smartmenu.client.category.CategoryRequest;
import com.smartmenu.common.basemodel.mapper.BaseUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
@RequiredArgsConstructor
public class CategoryUpdateMapper implements BaseUpdateMapper<CategoryRequest, Category> {

	final private CategoryMapper mapper;

	@Override
	public Category toEntityForUpdate(CategoryRequest request, Category entity) {
		if (entity == null) {
			entity = new Category();
		}

		if (request.getName() != null) {
			entity.setName(request.getName());
		}
		if (request.getOrder() != null) {
			entity.setOrder(request.getOrder());
		}
		if (request.getImgUrl() != null) {
			entity.setImgUrl(request.getImgUrl());
		}
		if (request.getStatus() != null) {
			entity.setStatus(request.getStatus());
		}

		return entity;
	}
}
