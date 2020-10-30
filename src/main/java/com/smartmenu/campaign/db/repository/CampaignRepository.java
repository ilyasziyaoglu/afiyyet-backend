package com.smartmenu.campaign.db.repository;

import com.smartmenu.campaign.db.entity.Campaign;
import com.smartmenu.common.basemodel.db.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface CampaignRepository extends BaseRepository<Campaign> {
	List<Campaign> findAllByBrandId(Long brandId);
}
