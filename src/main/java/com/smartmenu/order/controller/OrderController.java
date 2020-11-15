package com.smartmenu.order.controller;

import com.smartmenu.client.order.OrderRequest;
import com.smartmenu.client.order.OrderResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/order")
public class OrderController extends AbstractBaseController {
	private OrderService service;

	public OrderController(final OrderService service) {
		this.service = service;
	}

	public OrderService getService() {
		return service;
	}

	@GetMapping(GUEST + "/{id}")
	public ResponseEntity<ServiceResult<OrderResponse>> get(@PathVariable Long id) {
		ServiceResult<OrderResponse> serviceResult = service.get(id);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ServiceResult<OrderResponse>> save(@RequestHeader(HEADER_TOKEN) String token, @RequestBody OrderRequest request) {
		ServiceResult<OrderResponse> serviceResult = service.insert(request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<ServiceResult<OrderResponse>> update(@RequestHeader(HEADER_TOKEN) String token, @RequestBody OrderRequest request) {
		ServiceResult<OrderResponse> serviceResult = service.update(token, request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ServiceResult<Boolean>> delete(@RequestHeader(HEADER_TOKEN) String token, @Valid @PathVariable Long id) {
		ServiceResult<Boolean> serviceResult = service.delete(token, id);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<ServiceResult<OrderResponse>> save(@RequestBody OrderRequest request) {
		ServiceResult<OrderResponse> serviceResult = service.insert(request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@GetMapping("/cancel/{id}")
	public ResponseEntity<ServiceResult<Boolean>> cancelOrder(@RequestHeader(HEADER_TOKEN) String token, @PathVariable("id") Long id) {
		ServiceResult<Boolean> serviceResult = service.cancel(token, id);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

}
