package com.smartmenu.client.category;

import com.smartmenu.common.basemodel.response.BaseResponse;
import com.smartmenu.common.enums.Status;
import com.smartmenu.product.db.entity.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryResponse extends BaseResponse {
	private String name;
	private String imgUrl;
	private Long brandId;
	private Integer order;
	private List<Product> products;
	private Status status;
}
