package com.smartmenu.order.mapper;

import com.smartmenu.client.order.OrderRequest;
import com.smartmenu.client.order.OrderResponse;
import com.smartmenu.common.basemodel.mapper.BaseMapper;
import com.smartmenu.order.db.entity.Order;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<OrderRequest, Order, OrderResponse> {
}
