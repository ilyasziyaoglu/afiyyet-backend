package com.smartmenu.common.user.controller;

import com.smartmenu.client.user.UserRequest;
import com.smartmenu.client.user.UserResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.user.db.entity.User;
import com.smartmenu.common.user.mapper.UserMapper;
import com.smartmenu.common.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/user")
public class UserController extends AbstractBaseController<UserRequest, User, UserResponse, UserMapper, UserService> {
	private UserService service;
	private UserMapper mapper;

	public UserController(final UserService service, final UserMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	public UserService getService() {
		return service;
	}

	public UserMapper getMapper() {
		return mapper;
	}
}
