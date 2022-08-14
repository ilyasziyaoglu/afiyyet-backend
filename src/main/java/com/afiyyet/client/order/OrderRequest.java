package com.afiyyet.client.order;

import com.afiyyet.client.brand.BrandRequest;
import com.afiyyet.client.orderitem.OrderItemRequest;
import com.afiyyet.common.basemodel.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderRequest extends BaseRequest {
	private Long tableId;
	private BrandRequest brand;
	private BigDecimal totalPrice;
	private List<OrderItemRequest> orderItems;

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public BrandRequest getBrand() {
		return brand;
	}

	public void setBrand(BrandRequest brand) {
		this.brand = brand;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<OrderItemRequest> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemRequest> orderItems) {
		this.orderItems = orderItems;
	}
}
