package com.smartmenu.campaign.mapper;

import com.smartmenu.brand.mapper.BrandMapper;
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

	final private BrandMapper brandMapper;

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
		if (request.getBrand() != null) {
			entity.setBrand(brandMapper.toEntity(request.getBrand()));
		}
		if (request.getOrder() != null) {
			entity.setOrder(request.getOrder());
		}
		if (request.getDescription() != null) {
			entity.setDescription(request.getDescription());
		}
		if (request.getLikes() != null) {
			entity.setLikes(request.getLikes());
		}
		if (request.getStatus() != null) {
			entity.setStatus(request.getStatus());
		}
		if (request.getExpireDate() != null) {
			entity.setExpireDate(request.getExpireDate());
		}
		if (request.getStartDate() != null) {
			entity.setStartDate(request.getStartDate());
		}
		return entity;
	}
}
