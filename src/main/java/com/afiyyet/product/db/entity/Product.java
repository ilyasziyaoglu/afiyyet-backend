package com.afiyyet.product.db.entity;


import com.afiyyet.category.db.entity.Category;
import com.afiyyet.common.basemodel.db.entity.AbstractBaseEntity;
import com.afiyyet.common.enums.Status;
import com.afiyyet.product.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


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
	private Integer order;

	@Column(name = "expire_date")
	private ZonedDateTime expireDate;

	@Column(name = "start_date")
	private ZonedDateTime startDate;

	@Column(name = "has_portion_options")
	private Boolean hasPortionOption;

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getFakePrice() {
		return fakePrice;
	}

	public void setFakePrice(BigDecimal fakePrice) {
		this.fakePrice = fakePrice;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public ZonedDateTime getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(ZonedDateTime expireDate) {
		this.expireDate = expireDate;
	}

	public ZonedDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(ZonedDateTime startDate) {
		this.startDate = startDate;
	}

	public Boolean getHasPortionOption() {
		return hasPortionOption;
	}

	public void setHasPortionOption(Boolean hasPortionOption) {
		this.hasPortionOption = hasPortionOption;
	}
}
