package com.afiyyet.orderitem.db.entity;


import com.afiyyet.common.basemodel.db.entity.AbstractBaseEntity;
import com.afiyyet.order.db.entity.Order;
import com.afiyyet.orderitem.enums.OrderItemState;
import com.afiyyet.product.db.entity.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Entity
@Table(name = "orderitems")
public class OrderItem extends AbstractBaseEntity {

	private static final long serialVersionUID = 2818358983516690819L;

	@Id
	@Column(name = "id", nullable = false)
	@SequenceGenerator(name = "orderitems_id_gen", sequenceName = "orderitems_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "orderitems_id_gen", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "total_price")
	private BigDecimal totalPrice;

	@Column(name = "amount")
	private Integer amount;

	@Column(name = "portion")
	private Double portion;

	@Column(name = "comment")
	private String comment;

	@Column(name = "state")
	private OrderItemState state;

	@Column(name = "cancel_reason")
	private String cancelReason;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
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
