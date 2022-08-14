package com.afiyyet.order.mapper;

import com.afiyyet.client.order.OrderRequest;
import com.afiyyet.client.order.OrderResponse;
import com.afiyyet.common.basemodel.mapper.BaseMapper;
import com.afiyyet.order.db.entity.Order;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<OrderRequest, Order, OrderResponse> {
}
