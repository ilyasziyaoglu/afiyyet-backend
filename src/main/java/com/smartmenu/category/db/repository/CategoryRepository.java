package com.smartmenu.category.db.repository;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.category.db.entity.Category;
import com.smartmenu.common.basemodel.db.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface CategoryRepository extends BaseRepository<Category> {
	List<Category> findAllByBrand(Brand brand);
}
