package com.smartmenu.menu.model;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.category.db.entity.Category;
import com.smartmenu.product.db.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-10-03
 */

@Data
@AllArgsConstructor
public class Menu {
	private List<Category> categories;
	private List<Product> campaigns;
	private List<Product> menus;
	private Brand brand;
}
