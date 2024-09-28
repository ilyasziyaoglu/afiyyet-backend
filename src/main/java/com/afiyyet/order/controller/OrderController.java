package com.afiyyet.order.controller;

import com.afiyyet.client.order.OrderRequest;
import com.afiyyet.client.order.OrderResponse;
import com.afiyyet.client.order.OrderSummary;
import com.afiyyet.common.basemodel.controller.AbstractBaseController;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.order.criteria.OrderCriteria;
import com.afiyyet.order.service.OrderQueryService;
import com.afiyyet.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import java.util.List;
import java.util.Map;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/order")
public class OrderController extends AbstractBaseController {
	private final OrderService orderService;
	private OrderService service;
	private OrderQueryService orderQueryService;

	public OrderController(final OrderService service, final OrderQueryService orderQueryService,
		OrderService orderService
	) {
		this.service = service;
		this.orderQueryService = orderQueryService;
		this.orderService = orderService;
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

	@GetMapping("/filter")
	public ResponseEntity<ServiceResult<Page<OrderResponse>>> getAllOrders(
		@RequestHeader(HEADER_TOKEN) String token,
		OrderCriteria criteria,
		Pageable pageable
	) {
		Page<OrderResponse> page = orderService.findByCriteria(token, criteria, pageable);
		return ResponseEntity.ok().body(new ServiceResult<>(page));
	}

	@GetMapping("/summary")
	public ResponseEntity<ServiceResult<OrderSummary>> summary(
		@RequestHeader(HEADER_TOKEN) String token,
		OrderCriteria criteria
	) {
		OrderSummary orderCountAndTotal = orderService.summary(token, criteria);
		return ResponseEntity.ok().body(new ServiceResult<>(orderCountAndTotal));
	}

	@GetMapping("/round-per-table-per-hour")
	public ResponseEntity<ServiceResult<Double>> roundPerTablePerHour(
		@RequestHeader(HEADER_TOKEN) String token,
		OrderCriteria criteria
	) {
		Double roundPerTablePerHour = orderService.roundPerTablePerHour(token, criteria);
		return ResponseEntity.ok().body(new ServiceResult<>(roundPerTablePerHour));
	}

	@GetMapping("/hourly-distribution")
	public ResponseEntity<ServiceResult<Map<Integer, List<Double>>>> hourlyDsitribution(
		@RequestHeader(HEADER_TOKEN) String token,
		OrderCriteria criteria
	) {
		Map<Integer, List<Double>> hourlyDsitribution = orderService.hourlyDsitribution(token, criteria);
		return ResponseEntity.ok().body(new ServiceResult<>(hourlyDsitribution));
	}

}
