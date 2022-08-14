package com.afiyyet.client.orderitem;

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
public class OrderItemCancelRequest extends BaseRequest {
	private Integer cancelAmount;
	private String cancelReason;

	public Integer getCancelAmount() {
		return cancelAmount;
	}

	public void setCancelAmount(Integer cancelAmount) {
		this.cancelAmount = cancelAmount;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
}
