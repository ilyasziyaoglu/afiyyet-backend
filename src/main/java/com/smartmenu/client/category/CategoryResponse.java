package com.smartmenu.client.category;

import com.smartmenu.client.brand.BrandResponse;
import com.smartmenu.common.basemodel.response.BaseResponse;
import com.smartmenu.common.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryResponse extends BaseResponse {
	private String name;
	private Integer order;
	private String imgUrl;
	private Status status;
	private BrandResponse brand;
}
