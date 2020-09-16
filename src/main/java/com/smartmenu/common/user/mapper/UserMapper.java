package com.smartmenu.common.user.mapper;

import com.smartmenu.client.user.UserRequest;
import com.smartmenu.client.user.UserResponse;
import com.smartmenu.common.basemodel.mapper.BaseMapper;
import com.smartmenu.common.user.db.entity.User;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserRequest, User, UserResponse> {
}
