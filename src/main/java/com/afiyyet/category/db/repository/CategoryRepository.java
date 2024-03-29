package com.afiyyet.category.db.repository;

import com.afiyyet.category.db.entity.Category;
import com.afiyyet.common.basemodel.db.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface CategoryRepository extends BaseRepository<Category> {
	List<Category> findAllByBrandId(Long brandId);
	Category findAllByBrandIdAndName(Long brandId, String name);
}
