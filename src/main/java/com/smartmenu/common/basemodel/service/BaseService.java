package com.smartmenu.common.basemodel.service;

import com.smartmenu.common.basemodel.db.entity.AbstractBaseEntity;
import com.smartmenu.common.basemodel.db.repository.BaseRepository;
import com.smartmenu.common.basemodel.mapper.BaseUpdateMapper;
import com.smartmenu.common.basemodel.request.BaseRequest;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

public abstract class BaseService<Request extends BaseRequest, Entity extends AbstractBaseEntity, Repository extends BaseRepository<Entity>, UpdateMapper extends BaseUpdateMapper<Request, Entity>> {
	public abstract Repository getRepository();
	public abstract UpdateMapper getUpdateMapper();

}
