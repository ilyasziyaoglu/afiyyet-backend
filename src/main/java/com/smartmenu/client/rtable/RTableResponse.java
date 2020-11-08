package com.smartmenu.client.rtable;

import com.smartmenu.client.brand.BrandResponse;
import com.smartmenu.client.order.OrderResponse;
import com.smartmenu.common.basemodel.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class RTableResponse extends BaseResponse {
	private BrandResponse brand;
	private OrderResponse order;
	private String name;
	private String groupName;
	private Boolean isOpen;
}
