package com.smartmenu.product.db.entity;


import com.smartmenu.category.db.entity.Category;
import com.smartmenu.common.basemodel.db.entity.AbstractBaseEntity;
import com.smartmenu.common.enums.Status;
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
@Table(name = "products")
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractBaseEntity {

	private static final long serialVersionUID = -6997084654327883455L;

	@Id
	@Column(name = "id", nullable = false)
	@SequenceGenerator(name = "product_id_gen", sequenceName = "product_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "product_id_gen", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "img_url")
	private String imgUrl;

	@Column(name = "price")
	private BigDecimal price;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "description", length = 2000)
	private String description;

	@Column(name = "likes")
	private Integer likes = 0;

	@Column(name = "order_value")
	private Integer order;

	@Column(name = "status")
	private Status status;

	@Override
	public Long getId() {
		return this.id;
	}
}
