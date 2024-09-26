package com.afiyyet.category.db.entity;


import com.afiyyet.common.basemodel.db.entity.AbstractBaseEntity;
import com.afiyyet.common.enums.Status;
import com.afiyyet.product.db.entity.Product;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Entity
@NoArgsConstructor
@AllArgsConstructor
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

	public Category(String name, Integer order, Long brandId) {
		this.name = name;
		this.order = order;
		this.brandId = brandId;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
