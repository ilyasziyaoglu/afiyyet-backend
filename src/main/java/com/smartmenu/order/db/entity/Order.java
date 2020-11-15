package com.smartmenu.order.db.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.common.basemodel.db.entity.AbstractBaseEntity;
import com.smartmenu.orderitem.db.entity.OrderItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
@Entity
@Table(name = "orders")
@EqualsAndHashCode(callSuper = true)
public class Order extends AbstractBaseEntity {

	private static final long serialVersionUID = 2818358983516690819L;

	@Id
	@Column(name = "id", nullable = false)
	@SequenceGenerator(name = "orders_id_gen", sequenceName = "orders_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "orders_id_gen", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "table_id")
	private Long tableId;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@Column(name = "total_price")
	private BigDecimal totalPrice;

	@JsonManagedReference
	@OneToMany(mappedBy="order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<OrderItem> orderitems = new ArrayList<>();

	@Override
	public Long getId() {
		return this.id;
	}
}
