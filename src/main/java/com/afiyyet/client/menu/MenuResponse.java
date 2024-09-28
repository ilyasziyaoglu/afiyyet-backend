package com.afiyyet.client.menu;

import com.afiyyet.client.brand.BrandResponse;
import com.afiyyet.client.category.CategoryResponse;
import com.afiyyet.client.product.ProductResponse;
import com.afiyyet.common.basemodel.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MenuResponse extends BaseResponse {
	private List<CategoryResponse> categories;
	private List<ProductResponse> campaigns;
	private List<ProductResponse> menus;
	private BrandResponse brand;
}
