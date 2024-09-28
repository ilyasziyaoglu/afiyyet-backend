package com.afiyyet.orderitem.db.entity;


import com.afiyyet.common.basemodel.db.entity.AbstractBaseEntity;
import com.afiyyet.order.db.entity.Order;
import com.afiyyet.orderitem.enums.OrderItemState;
import com.afiyyet.product.db.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
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

	@ManyToOne(fetch = FetchType.EAGER)
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
}
