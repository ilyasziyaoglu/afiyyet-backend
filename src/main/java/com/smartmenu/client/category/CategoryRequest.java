package com.smartmenu.client.category;

import com.smartmenu.common.basemodel.request.BaseRequest;
import com.smartmenu.common.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryRequest extends BaseRequest {
	private String name;
	private Integer order;
	private String imgUrl;
	private Status status;
}
