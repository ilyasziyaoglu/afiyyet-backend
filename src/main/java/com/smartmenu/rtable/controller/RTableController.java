package com.smartmenu.rtable.controller;

import com.smartmenu.client.rtable.RTableRequest;
import com.smartmenu.client.rtable.RTableResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.rtable.db.entity.RTable;
import com.smartmenu.rtable.mapper.RTableMapper;
import com.smartmenu.rtable.service.RTableService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/rtable")
public class RTableController extends AbstractBaseController<RTableRequest, RTable, RTableResponse, RTableMapper, RTableService> {
	private RTableService service;
	private RTableMapper mapper;

	public RTableController(final RTableService service, final RTableMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	public RTableService getService() {
		return service;
	}

	public RTableMapper getMapper() {
		return mapper;
	}
}
