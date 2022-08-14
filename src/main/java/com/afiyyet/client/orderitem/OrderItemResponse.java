package com.afiyyet.client.orderitem;

import com.afiyyet.client.product.ProductResponse;
import com.afiyyet.common.basemodel.response.BaseResponse;
import com.afiyyet.orderitem.enums.OrderItemState;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderItemResponse extends BaseResponse {
	private Long orderId;
	private ProductResponse product;
	private BigDecimal totalPrice;
	private Integer amount;
	private Double portion;
	private String comment;
	private OrderItemState state;
	private String cancelReason;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public ProductResponse getProduct() {
		return product;
	}

	public void setProduct(ProductResponse product) {
		this.product = product;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getPortion() {
		return portion;
	}

	public void setPortion(Double portion) {
		this.portion = portion;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public OrderItemState getState() {
		return state;
	}

	public void setState(OrderItemState state) {
		this.state = state;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
}
