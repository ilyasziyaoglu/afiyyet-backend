package com.smartmenu.client.orderitem;

import com.smartmenu.client.order.OrderRequest;
import com.smartmenu.client.product.ProductRequest;
import com.smartmenu.common.basemodel.request.BaseRequest;
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
public class OrderItemRequest extends BaseRequest {
	private OrderRequest order;
	private ProductRequest product;
	private BigDecimal totalPrice;
	private Integer amount;
	private Boolean portion;
	private String comment;
	private OrderItemState state;
	private String cancelReason;
}
