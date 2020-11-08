package com.smartmenu.client.order;

import com.smartmenu.client.brand.BrandResponse;
import com.smartmenu.client.orderitem.OrderItemResponse;
import com.smartmenu.client.rtable.RTableResponse;
import com.smartmenu.common.basemodel.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderResponse extends BaseResponse {
	private RTableResponse table;
	private BrandResponse brand;
	private BigDecimal totalPrice;
	private List<OrderItemResponse> orderitems;
}
