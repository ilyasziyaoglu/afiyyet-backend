package com.smartmenu.client.orderitem;

import com.smartmenu.client.order.OrderResponse;
import com.smartmenu.client.product.ProductResponse;
import com.smartmenu.common.basemodel.response.BaseResponse;
import com.smartmenu.orderitem.enums.OrderItemState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItemResponse extends BaseResponse {
	private OrderResponse order;
	private ProductResponse product;
	private BigDecimal totalPrice;
	private Integer amount;
	private Boolean hasPortionOption;
	private String comment;
	private OrderItemState state;
	private String cancelReason;
}
