package com.smartmenu.client.brand;

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
public class BrandResponse extends BaseResponse {

	private String name;
	private String logoImgUrl;
	private Status status;
}
