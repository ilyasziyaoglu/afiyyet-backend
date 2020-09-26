package com.smartmenu.client.comment;

import com.smartmenu.client.brand.BrandRequest;
import com.smartmenu.common.basemodel.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CommentRequest extends BaseRequest {
	private String comment;
	private BrandRequest brand;
}
