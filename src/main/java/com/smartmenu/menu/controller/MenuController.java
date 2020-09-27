package com.smartmenu.menu.controller;

import com.smartmenu.campaign.db.entity.Campaign;
import com.smartmenu.campaign.mapper.CampaignMapper;
import com.smartmenu.category.db.entity.Category;
import com.smartmenu.category.mapper.CategoryMapper;
import com.smartmenu.client.campaign.CampaignResponse;
import com.smartmenu.client.category.CategoryResponse;
import com.smartmenu.client.menu.LikeRequest;
import com.smartmenu.common.basemodel.service.ServiceResult;
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
	final private CategoryMapper categoryMapper;
	final private CampaignMapper campaignMapper;

	@GetMapping("/{brandName}")
	public ResponseEntity<List<CategoryResponse>> getMenusByCategory(@PathVariable(name = "brandName") String brandName) {
		ServiceResult<List<Category>> serviceResult = service.getMenu(brandName);
		return new ResponseEntity<>(categoryMapper.toResponse(serviceResult.getValue()), serviceResult.getHttpStatus());
	}

	@GetMapping("/get-campaigns/{brandName}}")
	public ResponseEntity<List<CampaignResponse>> getCampaigns(@PathVariable(name = "brandName") String brandName) {
		ServiceResult<List<Campaign>> serviceResult = service.getCampaigns(brandName);
		return new ResponseEntity<>(campaignMapper.toResponse(serviceResult.getValue()), serviceResult.getHttpStatus());
	}

	@PostMapping("/like")
	public ResponseEntity<Boolean> getMenusByCategory(@RequestBody LikeRequest dto) {
		ServiceResult<Boolean> serviceResult = service.like(dto);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}
}
