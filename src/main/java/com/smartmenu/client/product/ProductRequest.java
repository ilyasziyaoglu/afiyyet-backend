package com.smartmenu.client.product;

import com.smartmenu.category.db.entity.Category;
import com.smartmenu.common.basemodel.request.BaseRequest;
import com.smartmenu.common.enums.Status;
import com.smartmenu.product.enums.ProductType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductRequest extends BaseRequest {
	private String name;
	private String imgUrl;
	private BigDecimal price;
	private BigDecimal fakePrice;
	private Category category;
	private String description;
	private Integer likes;
	private Integer order;
	private ProductType type;
	private Status status;
	private ZonedDateTime expireDate;
	private ZonedDateTime startDate;
	private Boolean hasPortionOption;
}
