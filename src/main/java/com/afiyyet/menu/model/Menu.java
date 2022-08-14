package com.afiyyet.menu.model;

import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.category.db.entity.Category;
import com.afiyyet.product.db.entity.Product;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-10-03
 */

@NoArgsConstructor
@AllArgsConstructor
public class Menu {
	private List<Category> categories;
	private List<Product> campaigns;
	private List<Product> menus;
	private Brand brand;

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Product> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<Product> campaigns) {
		this.campaigns = campaigns;
	}

	public List<Product> getMenus() {
		return menus;
	}

	public void setMenus(List<Product> menus) {
		this.menus = menus;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
}
