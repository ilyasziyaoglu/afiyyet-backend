package com.smartmenu.orderitem.controller;

import com.smartmenu.client.orderitem.OrderItemCancelRequest;
import com.smartmenu.client.orderitem.OrderItemRequest;
import com.smartmenu.client.orderitem.OrderItemResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.orderitem.service.OrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
