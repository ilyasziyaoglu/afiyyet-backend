package com.afiyyet.common.user.mapper;

import com.afiyyet.client.user.UserRequest;
import com.afiyyet.client.user.UserResponse;
import com.afiyyet.common.basemodel.mapper.BaseMapper;
import com.afiyyet.common.user.db.entity.User;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserRequest, User, UserResponse> {
}
