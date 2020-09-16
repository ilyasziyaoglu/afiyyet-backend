package com.smartmenu.common.property.service;

import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.property.db.entity.Property;
import com.smartmenu.common.property.db.repository.PropertyRepository;
import com.smartmenu.common.property.mapper.PropertyUpdateMapper;
import com.smartmenu.common.property.mapper.PropetyMapper;
import com.smartmenu.common.property.request.PropertyRequest;
import com.smartmenu.common.property.response.PropertyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class PropertyService extends AbstractBaseService<PropertyRequest, Property, PropertyResponse, PropetyMapper> {
    final private PropertyRepository repository;
    final private PropetyMapper mapper;
    final private PropertyUpdateMapper updateMapper;

    @Override
    public PropertyRepository getRepository() {
        return repository;
    }

    @Override
    public PropetyMapper getMapper() {
        return mapper;
    }

    @Override
    public PropertyUpdateMapper getUpdateMapper() {
        return updateMapper;
    }
}
