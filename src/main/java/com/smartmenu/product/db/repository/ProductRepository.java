package com.smartmenu.product.db.repository;

import com.smartmenu.common.basemodel.db.repository.BaseRepository;
import com.smartmenu.product.db.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface ProductRepository extends BaseRepository<Product> {
	List<Product> findAllByCategoryId(Long categoryId);

	@Query(value = "SELECT p.* from products p inner join categories c on p.category_id = c.id where c.brand_id=?1", nativeQuery = true)
	List<Product> findAllProductsByBrandId(@Param("brandId") Long brandId);
}
