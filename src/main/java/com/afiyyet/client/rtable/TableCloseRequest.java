package com.afiyyet.client.rtable;

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
public class TableCloseRequest extends BaseRequest {
	private Double discountAmount;
	private Boolean isPercent = true;

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Boolean getPercent() {
		return isPercent;
	}

	public void setPercent(Boolean percent) {
		isPercent = percent;
	}
}
