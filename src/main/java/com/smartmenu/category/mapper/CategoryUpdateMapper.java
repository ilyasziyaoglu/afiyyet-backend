package com.smartmenu.category.mapper;

import com.smartmenu.brand.mapper.BrandMapper;
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

	final private BrandMapper brandMapper;

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
		if (request.getBrand() != null) {
			entity.setBrand(brandMapper.toEntity(request.getBrand()));
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
