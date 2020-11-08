package com.smartmenu.orderitem.db.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.smartmenu.common.basemodel.db.entity.AbstractBaseEntity;
import com.smartmenu.order.db.entity.Order;
import com.smartmenu.orderitem.enums.OrderItemState;
import com.smartmenu.product.db.entity.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
@Entity
@Table(name = "orderitems")
@EqualsAndHashCode(callSuper = true)
public class OrderItem extends AbstractBaseEntity {

	private static final long serialVersionUID = 2818358983516690819L;

	@Id
	@Column(name = "id", nullable = false)
	@SequenceGenerator(name = "orderitems_id_gen", sequenceName = "orderitems_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "orderitems_id_gen", strategy = GenerationType.SEQUENCE)
	private Long id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "total_price")
	private BigDecimal totalPrice;

	@Column(name = "amount")
	private Integer amount;

	@Column(name = "portion")
	private Boolean portion;

	@Column(name = "comment")
	private String comment;

	@Column(name = "state")
	private OrderItemState state;

	@Column(name = "cancel_reason")
	private String cancelReason;

	@Override
	public Long getId() {
		return this.id;
	}
}
