package com.smartmenu.orderitem.controller;

import com.smartmenu.client.orderitem.OrderItemCancelRequest;
import com.smartmenu.client.orderitem.OrderItemRequest;
import com.smartmenu.client.orderitem.OrderItemResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.orderitem.db.entity.OrderItem;
import com.smartmenu.orderitem.mapper.OrderItemMapper;
import com.smartmenu.orderitem.service.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/orderitem")
public class OrderItemController extends AbstractBaseController<OrderItemRequest, OrderItem, OrderItemResponse, OrderItemMapper, OrderItemService> {
	private OrderItemService service;
	private OrderItemMapper mapper;

	public OrderItemController(final OrderItemService service, final OrderItemMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	public OrderItemService getService() {
		return service;
	}

	public OrderItemMapper getMapper() {
		return mapper;
	}

	@PostMapping("/cancel-from-menu")
	public ResponseEntity<Boolean> cancelOrder(@RequestBody OrderItemCancelRequest request) {
		ServiceResult<Boolean> serviceResult = service.cancelFromMenu(request);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}

	@PostMapping("/cancel-from-admin")
	public ResponseEntity<Boolean> cancelOrder(@RequestHeader(HEADER_TOKEN) String token, @RequestBody OrderItemCancelRequest request) {
		ServiceResult<Boolean> serviceResult = service.cancelFromAdminPanel(token, request);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}
}
