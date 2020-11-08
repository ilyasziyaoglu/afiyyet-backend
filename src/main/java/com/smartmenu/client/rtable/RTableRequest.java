package com.smartmenu.client.rtable;

import com.smartmenu.client.brand.BrandRequest;
import com.smartmenu.client.order.OrderRequest;
import com.smartmenu.common.basemodel.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class RTableRequest extends BaseRequest {
	private BrandRequest brand;
	private OrderRequest order;
	private String name;
	private String groupName;
	private Boolean isOpen;
}
