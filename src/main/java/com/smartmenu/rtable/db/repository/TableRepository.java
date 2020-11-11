package com.smartmenu.rtable.db.repository;

import com.smartmenu.common.basemodel.db.repository.BaseRepository;
import com.smartmenu.rtable.db.entity.RTable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface TableRepository extends BaseRepository<RTable> {
	List<RTable> findAllByBrandId(Long id);
}
