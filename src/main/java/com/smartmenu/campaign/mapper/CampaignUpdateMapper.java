package com.smartmenu.campaign.mapper;

import com.smartmenu.campaign.db.entity.Campaign;
import com.smartmenu.client.campaign.CampaignRequest;
import com.smartmenu.common.basemodel.mapper.BaseUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
@RequiredArgsConstructor
public class CampaignUpdateMapper implements BaseUpdateMapper<CampaignRequest, Campaign> {

	@Override
	public Campaign toEntityForUpdate(CampaignRequest request, Campaign entity) {
		if (!request.getName().isEmpty()) {
			entity.setName(request.getName());
		}
		if (request.getImgUrl() != null) {
			entity.setImgUrl(request.getImgUrl());
		}
		if (request.getPrice() != null) {
			entity.setPrice(request.getPrice());
		}
		if (request.getDescription() != null) {
			entity.setDescription(request.getDescription());
		}
		if (request.getStatus() != null) {
			entity.setStatus(request.getStatus());
		}
		return entity;
	}
}
