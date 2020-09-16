package com.smartmenu.product.db.repository;

import com.smartmenu.common.basemodel.db.repository.BaseRepository;
import com.smartmenu.product.db.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface ProductRepository extends BaseRepository<Product> {
	List<Product> findAllByName(String name);

	@Query(value = "select distinct product_id from product_colors where color_id in :colorIds", nativeQuery = true)
	Set<Long> findAllProductIdsByColorIn(@Param("colorIds") Set<Long> colorIds);

	@Query(value = "select distinct product_id from product_sizes where size_id in :sizeIds", nativeQuery = true)
	Set<Long> findAllProductIdsBySizeIds(@Param("sizeIds") Set<Long> sizeIds);
}
