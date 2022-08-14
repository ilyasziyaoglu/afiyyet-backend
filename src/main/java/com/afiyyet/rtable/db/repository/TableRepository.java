package com.afiyyet.rtable.db.repository;

import com.afiyyet.common.basemodel.db.repository.BaseRepository;
import com.afiyyet.rtable.db.entity.RTable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface TableRepository extends BaseRepository<RTable> {
	List<RTable> findAllByBrandId(Long brandId);

	@Query(value = "SELECT distinct group_name from tables t where t.brand_id=?1", nativeQuery = true)
	List<String> findGroupNamesByBrandId(@Param("brandId") Long brandId);
}
