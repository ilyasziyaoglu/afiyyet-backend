package com.smartmenu.rtable.controller;

import com.smartmenu.client.rtable.RTableRequest;
import com.smartmenu.client.rtable.RTableResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.rtable.db.entity.RTable;
import com.smartmenu.rtable.mapper.TableMapper;
import com.smartmenu.rtable.service.TableService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/table")
public class TableController extends AbstractBaseController<RTableRequest, RTable, RTableResponse, TableMapper, TableService> {
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
}
