package com.smartmenu.client.product;

import com.smartmenu.common.basemodel.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class BulkPriceUpdateRequest extends BaseRequest {
	private boolean isPercent = false;
	private double amount = 0;
}
