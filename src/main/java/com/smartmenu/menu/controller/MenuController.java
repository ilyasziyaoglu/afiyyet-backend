package com.smartmenu.menu.controller;

import com.smartmenu.campaign.db.entity.Campaign;
import com.smartmenu.campaign.mapper.CampaignMapper;
import com.smartmenu.client.campaign.CampaignResponse;
import com.smartmenu.client.menu.LikeRequest;
import com.smartmenu.client.menu.MenuResponse;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.menu.mapper.MenuMapper;
import com.smartmenu.menu.model.Menu;
import com.smartmenu.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/menu")
public class MenuController {
	final private MenuService service;
	final private MenuMapper menuMapper;
	final private CampaignMapper campaignMapper;

	@GetMapping("/{brandUniqueName}")
	public ResponseEntity<MenuResponse> getMenu(@PathVariable(name = "brandUniqueName") String brandUniqueName) {
		ServiceResult<Menu> serviceResult = service.getMenu(brandUniqueName);
		return new ResponseEntity<>(menuMapper.toResponse(serviceResult.getValue()), serviceResult.getHttpStatus());
	}

	@GetMapping("/get-campaigns/{brandUniqueName}}")
	public ResponseEntity<List<CampaignResponse>> getCampaigns(@PathVariable(name = "brandUniqueName") String brandUniqueName) {
		ServiceResult<List<Campaign>> serviceResult = service.getCampaigns(brandUniqueName);
		return new ResponseEntity<>(campaignMapper.toResponse(serviceResult.getValue()), serviceResult.getHttpStatus());
	}

	@PostMapping("/product-like")
	public ResponseEntity<Boolean> productLike(@RequestBody LikeRequest dto) {
		ServiceResult<Boolean> serviceResult = service.productLike(dto);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}

	@PostMapping("/campaign-like")
	public ResponseEntity<Boolean> campaignLike(@RequestBody LikeRequest dto) {
		ServiceResult<Boolean> serviceResult = service.campaignLike(dto);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}
}
