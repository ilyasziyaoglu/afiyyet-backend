package com.afiyyet.common.basemodel.mapper;

import com.afiyyet.common.basemodel.db.entity.AbstractBaseEntity;
import com.afiyyet.common.basemodel.request.BaseRequest;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-05-21
 */

public interface BaseUpdateMapper<Request extends BaseRequest, Entity extends AbstractBaseEntity> {
	Entity toEntityForUpdate(Request request, Entity entity);
}
