package com.afiyyet.client.product;

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
public class BulkPriceUpdateRequest extends BaseRequest {
	private boolean isPercent = false;
	private double amount = 0;

	public boolean isPercent() {
		return isPercent;
	}

	public void setPercent(boolean percent) {
		isPercent = percent;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
