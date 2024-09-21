package com.afiyyet.order.db.entity;


import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.common.basemodel.db.entity.AbstractBaseEntity;
import com.afiyyet.orderitem.db.entity.OrderItem;
import com.afiyyet.rtable.db.entity.RTable;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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

	@OneToOne
	@JoinColumn(name = "table_id")
	private RTable table;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@ToString.Include
	@Column(name = "total_price")
	private BigDecimal totalPrice;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new java.util.ArrayList<>();

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RTable getTable() {
		return table;
	}

	public void setTable(RTable table) {
		this.table = table;
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
