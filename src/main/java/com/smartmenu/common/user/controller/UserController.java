package com.smartmenu.common.user.controller;

import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/user")
public class UserController extends AbstractBaseController {
	private UserService service;

	public UserController(final UserService service) {
		this.service = service;
	}

	public UserService getService() {
		return service;
	}
}
