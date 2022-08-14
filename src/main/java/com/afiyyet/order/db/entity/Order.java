package com.afiyyet.order.db.entity;


import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.common.basemodel.db.entity.AbstractBaseEntity;
import com.afiyyet.orderitem.db.entity.OrderItem;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Entity
@Table(name = "orders")
public class Order extends AbstractBaseEntity {

	private static final long serialVersionUID = 2818358983516690819L;

	@ToString.Include
	@Id
	@Column(name = "id", nullable = false)
	@SequenceGenerator(name = "orders_id_gen", sequenceName = "orders_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_gen")
	private Long id;

	@ToString.Include
	@Column(name = "table_id")
	private Long tableId;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@ToString.Include
	@Column(name = "total_price")
	private BigDecimal totalPrice;

	@OneToMany(cascade = {CascadeType.ALL})
	private List<OrderItem> orderItems = new java.util.ArrayList<>();

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
}
