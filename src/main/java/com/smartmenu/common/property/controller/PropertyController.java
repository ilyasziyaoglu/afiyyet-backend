package com.smartmenu.common.property.controller;

import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.property.service.PropertyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/property")
public class PropertyController extends AbstractBaseController {
	private PropertyService service;

	public PropertyController(final PropertyService service) {
		this.service = service;
	}

	public PropertyService getService() {
		return service;
	}
}
