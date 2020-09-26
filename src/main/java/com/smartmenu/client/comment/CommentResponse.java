package com.smartmenu.client.comment;

import com.smartmenu.client.brand.BrandResponse;
import com.smartmenu.common.basemodel.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CommentResponse extends BaseResponse {
	private String comment;
	private BrandResponse brand;
}
