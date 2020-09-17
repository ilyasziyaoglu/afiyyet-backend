package com.smartmenu.product.db.repository;

import com.smartmenu.common.basemodel.db.repository.BaseRepository;
import com.smartmenu.product.db.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface ProductRepository extends BaseRepository<Product> {
	List<Product> findAllByCategoryId(Long categoryId);
}
