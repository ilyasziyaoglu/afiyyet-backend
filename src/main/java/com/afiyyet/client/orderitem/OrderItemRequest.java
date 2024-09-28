package com.afiyyet.client.orderitem;

import com.afiyyet.client.product.ProductRequest;
import com.afiyyet.common.basemodel.request.BaseRequest;
import com.afiyyet.orderitem.enums.OrderItemState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderItemRequest extends BaseRequest {
	private Long orderId;
	private ProductRequest product;
	private BigDecimal totalPrice;
	private Integer amount;
	private Double portion;
	private String comment;
	private OrderItemState state;
	private String cancelReason;
}
