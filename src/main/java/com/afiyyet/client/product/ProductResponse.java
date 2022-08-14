package com.afiyyet.client.product;

import com.afiyyet.category.db.entity.Category;
import com.afiyyet.common.basemodel.response.BaseResponse;
import com.afiyyet.common.enums.Status;
import com.afiyyet.product.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductResponse extends BaseResponse {
	private String name;
	private String imgUrl;
	private BigDecimal price;
	private BigDecimal fakePrice;
	private Category category;
	private String categoryName;
	private String description;
	private Integer likes;
	private Integer order;
	private ProductType type;
	private Status status;
	private ZonedDateTime expireDate;
	private ZonedDateTime startDate;
	private Boolean hasPortionOption;

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
