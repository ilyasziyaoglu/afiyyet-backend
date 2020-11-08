package com.smartmenu.common.basemodel.mapper;

import com.smartmenu.common.basemodel.db.entity.AbstractBaseEntity;
import com.smartmenu.common.basemodel.request.BaseRequest;
import com.smartmenu.common.basemodel.response.BaseResponse;

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
