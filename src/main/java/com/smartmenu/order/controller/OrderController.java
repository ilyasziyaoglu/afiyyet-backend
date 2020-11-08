package com.smartmenu.order.controller;

import com.smartmenu.client.order.OrderRequest;
import com.smartmenu.client.order.OrderResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.order.db.entity.Order;
import com.smartmenu.order.mapper.OrderMapper;
import com.smartmenu.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/order")
public class OrderController extends AbstractBaseController<OrderRequest, Order, OrderResponse, OrderMapper, OrderService> {
	private OrderService service;
	private OrderMapper mapper;

	public OrderController(final OrderService service, final OrderMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	public OrderService getService() {
		return service;
	}

	public OrderMapper getMapper() {
		return mapper;
	}

	@GetMapping("/cancel/{id}")
	public ResponseEntity<Boolean> cancelOrder(@RequestHeader(HEADER_TOKEN) String token, @PathVariable("id") Long id) {
		ServiceResult<Boolean> serviceResult = service.cancel(token, id);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}

}
