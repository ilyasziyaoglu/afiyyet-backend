package com.smartmenu.rtable.controller;

import com.smartmenu.client.rtable.TableCloseRequest;
import com.smartmenu.client.rtable.TableRequest;
import com.smartmenu.client.rtable.TableResponse;
import com.smartmenu.client.rtable.TableTransferRequest;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.rtable.db.entity.RTable;
import com.smartmenu.rtable.mapper.TableMapper;
import com.smartmenu.rtable.service.TableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/table")
public class TableController extends AbstractBaseController<TableRequest, RTable, TableResponse, TableMapper, TableService> {
	private TableService service;
	private TableMapper mapper;

	public TableController(final TableService service, final TableMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	public TableService getService() {
		return service;
	}

	public TableMapper getMapper() {
		return mapper;
	}

	@GetMapping("get-tables-by-brand")
	public ResponseEntity<List<RTable>> getTablesByBrand(@RequestHeader(HEADER_TOKEN) String token) {
		ServiceResult<List<RTable>> serviceResult = getService().getTablesByBrand(token);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}

	@PostMapping("close")
	public ResponseEntity<Boolean> close(@RequestHeader(HEADER_TOKEN) String token, @RequestBody TableCloseRequest request) {
		ServiceResult<Boolean> serviceResult = getService().close(token, request);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}

	@PostMapping("transfer")
	public ResponseEntity<Boolean> transfer(@RequestHeader(HEADER_TOKEN) String token, @RequestBody TableTransferRequest request) {
		ServiceResult<Boolean> serviceResult = getService().transfer(token, request);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}
}
