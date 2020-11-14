package com.smartmenu.menu.controller;

import com.smartmenu.client.menu.LikeRequest;
import com.smartmenu.client.menu.MenuResponse;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.menu.mapper.MenuMapper;
import com.smartmenu.menu.model.Menu;
import com.smartmenu.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@Cacheable("menu_by_brand")
	@GetMapping("/{brandUniqueName}")
	public ResponseEntity<MenuResponse> getMenu(@PathVariable(name = "brandUniqueName") String brandUniqueName) {
		ServiceResult<Menu> serviceResult = service.getMenu(brandUniqueName);
		return new ResponseEntity<>(menuMapper.toResponse(serviceResult.getValue()), serviceResult.getHttpStatus());
	}

	@PostMapping("/product-like")
	public ResponseEntity<Boolean> productLike(@RequestBody LikeRequest dto) {
		ServiceResult<Boolean> serviceResult = service.like(dto);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}
}
