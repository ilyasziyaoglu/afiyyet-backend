package com.afiyyet.brand.mapper;

import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.client.brand.BrandRequest;
import com.afiyyet.client.brand.BrandResponse;
import com.afiyyet.common.basemodel.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface BrandMapper extends BaseMapper<BrandRequest, Brand, BrandResponse> {
}
