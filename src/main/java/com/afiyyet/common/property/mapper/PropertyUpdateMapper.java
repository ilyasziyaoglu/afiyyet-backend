package com.afiyyet.common.property.mapper;

import com.afiyyet.common.basemodel.mapper.BaseUpdateMapper;
import com.afiyyet.common.property.db.entity.Property;
import com.afiyyet.common.property.request.PropertyRequest;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
public class PropertyUpdateMapper implements BaseUpdateMapper<PropertyRequest, Property> {

	@Override
	public Property toEntityForUpdate(PropertyRequest request, Property entity) {
		if (entity == null) {
			entity = new Property();
		}

		if (request.getKey() != null) {
			entity.setKey(request.getKey());
		}

		if (request.getValue() != null) {
			entity.setValue(request.getValue());
		}
		return entity;
	}
}
