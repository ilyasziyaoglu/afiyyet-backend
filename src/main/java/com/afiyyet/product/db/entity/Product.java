package com.afiyyet.product.db.entity;


import com.afiyyet.category.db.entity.Category;
import com.afiyyet.common.basemodel.db.entity.AbstractBaseEntity;
import com.afiyyet.common.enums.Status;
import com.afiyyet.product.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
import jakarta.persistence.Transient;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
@Entity
@Table(name = "products")
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

	@Column(name = "fake_price")
	private BigDecimal fakePrice;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Transient
	private String categoryName;

	@Column(name = "description", length = 2000)
	private String description;

	@Column(name = "type")
	private ProductType type = ProductType.PRODUCT;

	@Column(name = "status")
	private Status status;

	@Column(name = "likes")
	private Integer likes = 0;

	@Column(name = "order_value")
	private Integer order = Integer.MAX_VALUE;

	@Column(name = "expire_date")
	private ZonedDateTime expireDate;

	@Column(name = "start_date")
	private ZonedDateTime startDate;

	@Column(name = "has_portion_options")
	private Boolean hasPortionOption;
}
