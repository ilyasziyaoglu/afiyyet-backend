package com.afiyyet.menu.controller;

import com.afiyyet.client.menu.LikeRequest;
import com.afiyyet.client.menu.MenuResponse;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
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

	@Cacheable("menu_by_brand")
	@GetMapping("/{brandUniqueName}")
	public ResponseEntity<ServiceResult<MenuResponse>> getMenu(@PathVariable(name = "brandUniqueName") String brandUniqueName) {
		ServiceResult<MenuResponse> serviceResult = service.getMenu(brandUniqueName);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping("/product-like")
	public ResponseEntity<ServiceResult<Boolean>> productLike(@RequestBody LikeRequest dto) {
		ServiceResult<Boolean> serviceResult = service.like(dto);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}
}
