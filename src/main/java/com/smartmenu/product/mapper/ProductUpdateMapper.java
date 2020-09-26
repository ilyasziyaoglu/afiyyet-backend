package com.smartmenu.product.mapper;

import com.smartmenu.category.mapper.CategoryMapper;
import com.smartmenu.client.product.ProductRequest;
import com.smartmenu.common.basemodel.mapper.BaseUpdateMapper;
import com.smartmenu.product.db.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
@RequiredArgsConstructor
public class ProductUpdateMapper implements BaseUpdateMapper<ProductRequest, Product> {

	final private CategoryMapper categoryMapper;

	@Override
	public Product toEntityForUpdate(ProductRequest request, Product entity) {
		if (!request.getName().isEmpty()) {
			entity.setName(request.getName());
		}
		if (request.getImgUrl() != null) {
			entity.setImgUrl(request.getImgUrl());
		}
		if (request.getPrice() != null) {
			entity.setPrice(request.getPrice());
		}
//		if (request.getCategory() != null) {
//			entity.setCategory(categoryMapper.toEntity(request.getCategory()));
//		}
		if (request.getLikes() != null) {
			entity.setLikes(request.getLikes());
		}
		if (request.getOrder() != null) {
			entity.setOrder(request.getOrder());
		}
		if (request.getDescription() != null) {
			entity.setDescription(request.getDescription());
		}
		if (request.getStatus() != null) {
			entity.setStatus(request.getStatus());
		}
		return entity;
	}
}
