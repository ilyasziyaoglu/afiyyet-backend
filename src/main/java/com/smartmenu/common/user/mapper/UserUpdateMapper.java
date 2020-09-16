package com.smartmenu.common.user.mapper;

import com.smartmenu.client.user.UserRequest;
import com.smartmenu.common.basemodel.mapper.BaseUpdateMapper;
import com.smartmenu.common.user.db.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
public class UserUpdateMapper implements BaseUpdateMapper<UserRequest, User> {

	@Override
	public User toEntityForUpdate(UserRequest request, User entity) {
		if (entity == null) {
			entity = new User();
		}

		return entity;
	}
}
