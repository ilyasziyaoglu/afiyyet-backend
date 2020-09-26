package com.smartmenu.campaign.controller;

import com.smartmenu.campaign.db.entity.Campaign;
import com.smartmenu.campaign.mapper.CampaignMapper;
import com.smartmenu.campaign.service.CampaignService;
import com.smartmenu.client.brand.BrandRequest;
import com.smartmenu.client.campaign.CampaignRequest;
import com.smartmenu.client.campaign.CampaignResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.basemodel.service.ServiceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

	@PostMapping("/arrange-campaigns")
	public ResponseEntity<Boolean> arrangeCampaigns(@RequestHeader(HEADER_TOKEN) String token, @RequestBody Map<Long, Integer> dto) {
		ServiceResult<Boolean> serviceResult = getService().arrangeCampaigns(token, dto);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}

	@PostMapping("/get-campaigns-by-brand}")
	public ResponseEntity<List<Campaign>> getCampaignsByBrand(@RequestHeader(HEADER_TOKEN) String token, @RequestBody BrandRequest dto) {
		ServiceResult<List<Campaign>> serviceResult = getService().getCampaignsByBrand(token, dto);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}
}
