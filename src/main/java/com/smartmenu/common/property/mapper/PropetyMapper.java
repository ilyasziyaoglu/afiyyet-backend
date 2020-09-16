package com.smartmenu.common.property.mapper;

import com.smartmenu.common.basemodel.mapper.BaseMapper;
import com.smartmenu.common.property.db.entity.Property;
import com.smartmenu.common.property.request.PropertyRequest;
import com.smartmenu.common.property.response.PropertyResponse;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface PropetyMapper extends BaseMapper<PropertyRequest, Property, PropertyResponse> {
}
