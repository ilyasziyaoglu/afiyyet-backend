package com.afiyyet.brand.db.repository;

import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.common.basemodel.db.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface BrandRepository extends BaseRepository<Brand> {
	Brand findByUniqueName(String uniqueName);
}
