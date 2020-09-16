package com.smartmenu.brand.mapper;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.client.brand.BrandRequest;
import com.smartmenu.client.brand.BrandResponse;
import com.smartmenu.common.basemodel.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface BrandMapper extends BaseMapper<BrandRequest, Brand, BrandResponse> {
}
