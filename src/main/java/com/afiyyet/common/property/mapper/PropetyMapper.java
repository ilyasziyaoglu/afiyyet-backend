package com.afiyyet.common.property.mapper;

import com.afiyyet.common.basemodel.mapper.BaseMapper;
import com.afiyyet.common.property.db.entity.Property;
import com.afiyyet.common.property.request.PropertyRequest;
import com.afiyyet.common.property.response.PropertyResponse;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface PropetyMapper extends BaseMapper<PropertyRequest, Property, PropertyResponse> {
}
