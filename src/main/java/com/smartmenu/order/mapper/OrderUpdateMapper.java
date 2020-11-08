package com.smartmenu.order.mapper;

import com.smartmenu.brand.mapper.BrandMapper;
import com.smartmenu.client.order.OrderRequest;
import com.smartmenu.common.basemodel.mapper.BaseUpdateMapper;
import com.smartmenu.order.db.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
@RequiredArgsConstructor
public class OrderUpdateMapper implements BaseUpdateMapper<OrderRequest, Order> {

	final private BrandMapper brandMapper;

	@Override
	public Order toEntityForUpdate(OrderRequest request, Order entity) {
		if (entity == null) {
			entity = new Order();
		}

		if (request.getBrand() != null) {
			entity.setBrand(brandMapper.toEntity(request.getBrand()));
		}

		return entity;
	}
}
