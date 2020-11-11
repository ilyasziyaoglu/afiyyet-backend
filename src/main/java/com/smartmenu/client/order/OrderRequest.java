package com.smartmenu.client.order;

import com.smartmenu.client.brand.BrandRequest;
import com.smartmenu.client.orderitem.OrderItemRequest;
import com.smartmenu.client.rtable.TableRequest;
import com.smartmenu.common.basemodel.request.BaseRequest;
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
public class OrderRequest extends BaseRequest {
	private TableRequest table;
	private BrandRequest brand;
	private BigDecimal totalPrice;
	private List<OrderItemRequest> orderitems;
}
