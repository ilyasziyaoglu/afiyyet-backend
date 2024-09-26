package com.afiyyet.orderitem.controller;

import com.afiyyet.client.orderitem.OrderItemCancelRequest;
import com.afiyyet.client.orderitem.OrderItemRequest;
import com.afiyyet.client.orderitem.OrderItemResponse;
import com.afiyyet.common.basemodel.controller.AbstractBaseController;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.orderitem.service.OrderItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/orderitem")
public class OrderItemController extends AbstractBaseController {
	private OrderItemService service;

	public OrderItemController(final OrderItemService service) {
		this.service = service;
	}

	public OrderItemService getService() {
		return service;
	}

	@GetMapping(GUEST + "/{id}")
	public ResponseEntity<ServiceResult<OrderItemResponse>> get(@PathVariable Long id) {
		ServiceResult<OrderItemResponse> serviceResult = service.get(id);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ServiceResult<OrderItemResponse>> save(@RequestHeader(HEADER_TOKEN) String token, @RequestBody OrderItemRequest request) {
		ServiceResult<OrderItemResponse> serviceResult = service.insert(request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<ServiceResult<OrderItemResponse>> update(@RequestHeader(HEADER_TOKEN) String token, @RequestBody OrderItemRequest request) {
		ServiceResult<OrderItemResponse> serviceResult = service.update(token, request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ServiceResult<Boolean>> delete(@RequestHeader(HEADER_TOKEN) String token, @Valid @PathVariable Long id) {
		ServiceResult<Boolean> serviceResult = service.delete(token, id);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping("/update-from-menu")
	public ResponseEntity<ServiceResult<OrderItemResponse>> updateFromMenu(@RequestBody OrderItemRequest request) {
		ServiceResult<OrderItemResponse> serviceResult = service.updateFromMenu(request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping("/cancel-from-menu")
	public ResponseEntity<ServiceResult<Boolean>> cancelOrder(@RequestBody OrderItemCancelRequest request) {
		ServiceResult<Boolean> serviceResult = service.cancelFromMenu(request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping("/cancel-from-admin")
	public ResponseEntity<ServiceResult<Boolean>> cancelOrder(@RequestHeader(HEADER_TOKEN) String token, @RequestBody OrderItemCancelRequest request) {
		ServiceResult<Boolean> serviceResult = service.cancelFromAdminPanel(token, request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}
}
