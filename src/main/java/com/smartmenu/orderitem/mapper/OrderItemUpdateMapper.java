package com.smartmenu.orderitem.mapper;

import com.smartmenu.brand.mapper.BrandMapper;
import com.smartmenu.client.orderitem.OrderItemRequest;
import com.smartmenu.common.basemodel.mapper.BaseUpdateMapper;
import com.smartmenu.orderitem.db.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
@RequiredArgsConstructor
public class OrderItemUpdateMapper implements BaseUpdateMapper<OrderItemRequest, OrderItem> {

	final private BrandMapper brandMapper;

	@Override
	public OrderItem toEntityForUpdate(OrderItemRequest request, OrderItem entity) {
		if (entity == null) {
			entity = new OrderItem();
		}

		return entity;
	}
}
