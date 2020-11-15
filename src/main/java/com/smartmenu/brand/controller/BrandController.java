package com.smartmenu.brand.controller;

import com.smartmenu.brand.service.BrandService;
import com.smartmenu.client.brand.BrandRequest;
import com.smartmenu.client.brand.BrandResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.basemodel.service.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/brand")
public class BrandController extends AbstractBaseController {
	private BrandService service;

	public BrandController(final BrandService service) {
		this.service = service;
	}

	public BrandService getService() {
		return service;
	}

	@GetMapping(GUEST + "/{id}")
	public ResponseEntity<ServiceResult<BrandResponse>> get(@PathVariable Long id) {
		ServiceResult<BrandResponse> serviceResult = service.get(id);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ServiceResult<BrandResponse>> save(@RequestHeader(HEADER_TOKEN) String token, @RequestBody BrandRequest request) {
		ServiceResult<BrandResponse> serviceResult = service.insert(token, request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<ServiceResult<BrandResponse>> update(@RequestHeader(HEADER_TOKEN) String token, @RequestBody BrandRequest request) {
		ServiceResult<BrandResponse> serviceResult = service.update(token, request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ServiceResult<Boolean>> delete(@RequestHeader(HEADER_TOKEN) String token, @Valid @PathVariable Long id) {
		ServiceResult<Boolean> serviceResult = service.delete(token, id);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}
}
