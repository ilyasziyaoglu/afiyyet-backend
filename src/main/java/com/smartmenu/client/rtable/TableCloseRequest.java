package com.smartmenu.client.rtable;

import com.smartmenu.common.basemodel.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class TableCloseRequest extends BaseRequest {
	private Double discountAmount;
	private Boolean isPercent = true;
}
