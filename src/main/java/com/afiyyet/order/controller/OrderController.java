package com.afiyyet.order.controller;

import com.afiyyet.client.order.OrderRequest;
import com.afiyyet.client.order.OrderResponse;
import com.afiyyet.common.basemodel.controller.AbstractBaseController;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.order.criteria.OrderCriteria;
import com.afiyyet.order.service.OrderQueryService;
import com.afiyyet.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/order")
public class OrderController extends AbstractBaseController {
	private OrderService service;
	private OrderQueryService orderQueryService;

	public OrderController(final OrderService service, final OrderQueryService orderQueryService) {
		this.service = service;
		this.orderQueryService = orderQueryService;
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

	/**
	 * {@code GET  /orders} : get all the orders.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orders in body.
	 */
	@GetMapping("/filter")
	public ResponseEntity<ServiceResult<Page<OrderResponse>>> getAllOrders(
		OrderCriteria criteria,
		Pageable pageable
	) {
		Page<OrderResponse> page = orderQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = tech.jhipster.web.util.PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(new ServiceResult<>(page));
	}

}
