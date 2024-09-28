package com.afiyyet.client.order;

import com.afiyyet.client.brand.BrandResponse;
import com.afiyyet.client.orderitem.OrderItemResponse;
import com.afiyyet.common.basemodel.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderResponse extends BaseResponse {
	private Long tableId;
	private BrandResponse brand;
	private BigDecimal totalPrice;
	private List<OrderItemResponse> orderItems;
}
