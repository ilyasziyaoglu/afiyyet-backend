package com.smartmenu.rtable.controller;

import com.smartmenu.client.rtable.TableCloseRequest;
import com.smartmenu.client.rtable.TableRequest;
import com.smartmenu.client.rtable.TableResponse;
import com.smartmenu.client.rtable.TableTransferRequest;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.rtable.service.TableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
}
