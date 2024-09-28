package com.afiyyet.client.product;

import com.afiyyet.category.db.entity.Category;
import com.afiyyet.common.basemodel.request.BaseRequest;
import com.afiyyet.common.enums.Status;
import com.afiyyet.product.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
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
