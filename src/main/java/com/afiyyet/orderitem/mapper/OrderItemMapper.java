package com.afiyyet.orderitem.mapper;

import com.afiyyet.client.orderitem.OrderItemRequest;
import com.afiyyet.client.orderitem.OrderItemResponse;
import com.afiyyet.common.basemodel.mapper.BaseMapper;
import com.afiyyet.orderitem.db.entity.OrderItem;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface OrderItemMapper extends BaseMapper<OrderItemRequest, OrderItem, OrderItemResponse> {
}
