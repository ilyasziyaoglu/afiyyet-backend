package com.afiyyet.menu.model;

import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.category.db.entity.Category;
import com.afiyyet.product.db.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-10-03
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
	private List<Category> categories;
	private List<Product> campaigns;
	private List<Product> menus;
	private Brand brand;
}
