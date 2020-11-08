package com.smartmenu.orderitem.mapper;

import com.smartmenu.client.orderitem.OrderItemRequest;
import com.smartmenu.client.orderitem.OrderItemResponse;
import com.smartmenu.common.basemodel.mapper.BaseMapper;
import com.smartmenu.orderitem.db.entity.OrderItem;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface OrderItemMapper extends BaseMapper<OrderItemRequest, OrderItem, OrderItemResponse> {
}
