package com.smartmenu.common.basemodel.mapper;

import com.smartmenu.common.basemodel.db.entity.AbstractBaseEntity;
import com.smartmenu.common.basemodel.request.BaseRequest;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-05-21
 */

public interface BaseUpdateMapper<Request extends BaseRequest, Entity extends AbstractBaseEntity> {
	Entity toEntityForUpdate(Request request, Entity entity);
}
