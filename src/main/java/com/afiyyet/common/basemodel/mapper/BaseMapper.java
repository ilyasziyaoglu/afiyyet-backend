package com.afiyyet.common.basemodel.mapper;

import com.afiyyet.common.basemodel.db.entity.AbstractBaseEntity;
import com.afiyyet.common.basemodel.request.BaseRequest;
import com.afiyyet.common.basemodel.response.BaseResponse;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

public interface BaseMapper<Request extends BaseRequest, Entity extends AbstractBaseEntity, Response extends BaseResponse> {
    Entity toEntity(Request request);
    Response toResponse(Entity entity);
    List<Entity> toEntity(List<Request> requestList);
    List<Response> toResponse(List<Entity> requestList);
}
