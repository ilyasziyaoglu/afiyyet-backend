package com.smartmenu.category.db.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.common.basemodel.db.entity.AbstractBaseEntity;
import com.smartmenu.common.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
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

	@Column(name = "order_value")
	private Integer order;

	@Column(name = "image")
	private String imgUrl;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@Column(name = "status")
	private Status status = Status.ACTIVE;

	@Override
	public Long getId() {
		return this.id;
	}
}