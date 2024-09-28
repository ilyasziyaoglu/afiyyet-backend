package com.afiyyet.rtable.controller;

import com.afiyyet.client.rtable.TableCloseRequest;
import com.afiyyet.client.rtable.TableRequest;
import com.afiyyet.client.rtable.TableResponse;
import com.afiyyet.client.rtable.TableTransferRequest;
import com.afiyyet.common.basemodel.controller.AbstractBaseController;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.rtable.service.TableService;
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

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/table")
public class TableController extends AbstractBaseController {
	private TableService service;

	public TableController(final TableService service) {
		this.service = service;
	}

	public TableService getService() {
		return service;
	}

	@GetMapping(GUEST + "/{id}")
	public ResponseEntity<ServiceResult<TableResponse>> get(@PathVariable Long id) {
		ServiceResult<TableResponse> serviceResult = service.get(id);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ServiceResult<TableResponse>> save(@RequestHeader(HEADER_TOKEN) String token, @RequestBody TableRequest request) {
		ServiceResult<TableResponse> serviceResult = service.insert(token, request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<ServiceResult<TableResponse>> update(@RequestHeader(HEADER_TOKEN) String token, @RequestBody TableRequest request) {
		ServiceResult<TableResponse> serviceResult = service.update(token, request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ServiceResult<Boolean>> delete(@RequestHeader(HEADER_TOKEN) String token, @Valid @PathVariable Long id) {
		ServiceResult<Boolean> serviceResult = service.delete(token, id);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@GetMapping("get-tables-by-brand")
	public ResponseEntity<ServiceResult<List<TableResponse>>> getTablesByBrand(@RequestHeader(HEADER_TOKEN) String token) {
		ServiceResult<List<TableResponse>> serviceResult = getService().getTablesByBrand(token);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@GetMapping("get-group-names-by-brand")
	public ResponseEntity<ServiceResult<List<String>>> getGroupNamesByBrand(@RequestHeader(HEADER_TOKEN) String token) {
		ServiceResult<List<String>> serviceResult = getService().getGroupNamesByBrand(token);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping("close")
	public ResponseEntity<ServiceResult<Boolean>> close(@RequestHeader(HEADER_TOKEN) String token, @RequestBody TableCloseRequest request) {
		ServiceResult<Boolean> serviceResult = getService().close(token, request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping("transfer")
	public ResponseEntity<ServiceResult<Boolean>> transfer(@RequestHeader(HEADER_TOKEN) String token, @RequestBody TableTransferRequest request) {
		ServiceResult<Boolean> serviceResult = getService().transfer(token, request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@GetMapping("restaurant-occupation")
	public ResponseEntity<ServiceResult<Double>> restaurantOccupation(@RequestHeader(HEADER_TOKEN) String token) {
		ServiceResult<List<TableResponse>> serviceResult = getService().getTablesByBrand(token);
		double result = serviceResult.getValue().stream().filter(TableResponse::getIsOpen).count() / (double) serviceResult.getValue().size();
		return new ResponseEntity<>(new ServiceResult<>(Math.round(result*10000.0)/100.0), HttpStatus.OK);
	}
}
