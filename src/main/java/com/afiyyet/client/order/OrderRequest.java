package com.afiyyet.client.order;

import com.afiyyet.client.brand.BrandRequest;
import com.afiyyet.client.orderitem.OrderItemRequest;
import com.afiyyet.common.basemodel.request.BaseRequest;
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
public class OrderRequest extends BaseRequest {
	private Long tableId;
	private BrandRequest brand;
	private BigDecimal totalPrice;
	private List<OrderItemRequest> orderItems;
}
