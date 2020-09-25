package com.smartmenu.campaign.controller;

import com.smartmenu.campaign.db.entity.Campaign;
import com.smartmenu.campaign.mapper.CampaignMapper;
import com.smartmenu.campaign.service.CampaignService;
import com.smartmenu.client.campaign.CampaignRequest;
import com.smartmenu.client.campaign.CampaignResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/campaign")
public class CampaignController extends AbstractBaseController<CampaignRequest, Campaign, CampaignResponse, CampaignMapper,
		CampaignService> {
	private CampaignService service;
	private CampaignMapper mapper;

	public CampaignController(final CampaignService service, final CampaignMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	public CampaignService getService() {
		return service;
	}

	public CampaignMapper getMapper() {
		return mapper;
	}
}
