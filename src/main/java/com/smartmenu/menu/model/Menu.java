package com.smartmenu.menu.model;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.campaign.db.entity.Campaign;
import com.smartmenu.category.db.entity.Category;
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
	private List<Campaign> campaigns;
	private Brand brand;
}
