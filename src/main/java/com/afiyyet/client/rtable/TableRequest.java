package com.afiyyet.client.rtable;

import com.afiyyet.client.brand.BrandRequest;
import com.afiyyet.client.order.OrderRequest;
import com.afiyyet.common.basemodel.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TableRequest extends BaseRequest {
	private BrandRequest brand;
	private OrderRequest order;
	private String name;
	private String groupName;
	private Boolean isOpen;

	public BrandRequest getBrand() {
		return brand;
	}

	public void setBrand(BrandRequest brand) {
		this.brand = brand;
	}

	public OrderRequest getOrder() {
		return order;
	}

	public void setOrder(OrderRequest order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Boolean getOpen() {
		return isOpen;
	}

	public void setOpen(Boolean open) {
		isOpen = open;
	}
}
