package com.smartmenu.orderitem.controller;

import com.smartmenu.client.orderitem.OrderItemRequest;
import com.smartmenu.client.orderitem.OrderItemResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.orderitem.db.entity.OrderItem;
import com.smartmenu.orderitem.mapper.OrderItemMapper;
import com.smartmenu.orderitem.service.OrderItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
