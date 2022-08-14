package com.afiyyet.client.menu;

import com.afiyyet.client.brand.BrandResponse;
import com.afiyyet.client.category.CategoryResponse;
import com.afiyyet.client.product.ProductResponse;
import com.afiyyet.common.basemodel.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MenuResponse extends BaseResponse {
	private List<CategoryResponse> categories;
	private List<ProductResponse> campaigns;
	private List<ProductResponse> menus;
	private BrandResponse brand;

	public List<CategoryResponse> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryResponse> categories) {
		this.categories = categories;
	}

	public List<ProductResponse> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<ProductResponse> campaigns) {
		this.campaigns = campaigns;
	}

	public List<ProductResponse> getMenus() {
		return menus;
	}

	public void setMenus(List<ProductResponse> menus) {
		this.menus = menus;
	}

	public BrandResponse getBrand() {
		return brand;
	}

	public void setBrand(BrandResponse brand) {
		this.brand = brand;
	}
}
