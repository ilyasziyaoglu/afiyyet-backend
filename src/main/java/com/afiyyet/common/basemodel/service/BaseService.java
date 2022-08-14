package com.afiyyet.common.basemodel.service;

import com.afiyyet.common.basemodel.db.entity.AbstractBaseEntity;
import com.afiyyet.common.basemodel.db.repository.BaseRepository;
import com.afiyyet.common.basemodel.mapper.BaseUpdateMapper;
import com.afiyyet.common.basemodel.request.BaseRequest;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

public abstract class BaseService<Request extends BaseRequest, Entity extends AbstractBaseEntity, Repository extends BaseRepository<Entity>, UpdateMapper extends BaseUpdateMapper<Request, Entity>> {
	public abstract Repository getRepository();
	public abstract UpdateMapper getUpdateMapper();
}
