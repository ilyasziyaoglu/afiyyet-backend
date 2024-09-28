package com.afiyyet.order.db.entity;


import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.common.basemodel.db.entity.AbstractBaseEntity;
import com.afiyyet.orderitem.db.entity.OrderItem;
import com.afiyyet.rtable.db.entity.RTable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.StaticMetamodel;
import lombok.Data;
import lombok.ToString;

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
@StaticMetamodel(value = Order.class)
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

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<OrderItem> orderItems = new ArrayList<>();
}
