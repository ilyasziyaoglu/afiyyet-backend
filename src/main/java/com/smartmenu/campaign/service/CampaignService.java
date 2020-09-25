package com.smartmenu.campaign.service;

import com.smartmenu.campaign.db.entity.Campaign;
import com.smartmenu.campaign.db.repository.CampaignRepository;
import com.smartmenu.campaign.mapper.CampaignMapper;
import com.smartmenu.campaign.mapper.CampaignUpdateMapper;
import com.smartmenu.client.campaign.CampaignRequest;
import com.smartmenu.client.campaign.CampaignResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class CampaignService extends AbstractBaseService<CampaignRequest, Campaign, CampaignResponse, CampaignMapper> {
	final private CampaignRepository repository;
	final private CampaignMapper mapper;
	final private CampaignUpdateMapper updateMapper;

	@Override
	public CampaignRepository getRepository() {
		return repository;
	}

	@Override
	public CampaignMapper getMapper() {
		return mapper;
	}

	@Override
	public CampaignUpdateMapper getUpdateMapper() {
		return updateMapper;
	}

}
