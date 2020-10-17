package com.smartmenu.category.db.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.smartmenu.common.basemodel.db.entity.AbstractBaseEntity;
import com.smartmenu.common.enums.Status;
import com.smartmenu.product.db.entity.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "categories")
public class Category extends AbstractBaseEntity {

	private static final long serialVersionUID = 2818358983516690819L;

	@Id
	@Column(name = "id", nullable = false)
	@SequenceGenerator(name = "categories_id_gen", sequenceName = "categories_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "categories_id_gen", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "image")
	private String imgUrl;

	@Column(name = "brand_id")
	private Long brandId;

	@Column(name = "order_value")
	private Integer order;

	@JsonManagedReference
	@OneToMany(mappedBy="category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Product> products;

	@Column(name = "status")
	private Status status = Status.ACTIVE;

	@Override
	public Long getId() {
		return this.id;
	}
}
