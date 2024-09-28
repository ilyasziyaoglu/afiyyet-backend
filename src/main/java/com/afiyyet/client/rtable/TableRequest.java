package com.afiyyet.client.rtable;

import com.afiyyet.client.brand.BrandRequest;
import com.afiyyet.client.order.OrderRequest;
import com.afiyyet.common.basemodel.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TableRequest extends BaseRequest {
	private BrandRequest brand;
	private OrderRequest order;
	private String name;
	private String groupName;
	private Boolean isOpen;
}
