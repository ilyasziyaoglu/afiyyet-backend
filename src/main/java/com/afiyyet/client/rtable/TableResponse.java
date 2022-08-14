package com.afiyyet.client.rtable;

import com.afiyyet.client.order.OrderResponse;
import com.afiyyet.common.basemodel.response.BaseResponse;
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
public class TableResponse extends BaseResponse {
	private OrderResponse order;
	private String name;
	private String groupName;
	private Boolean isOpen;

	public OrderResponse getOrder() {
		return order;
	}

	public void setOrder(OrderResponse order) {
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
