package com.smartmenu.product.mapper;

import com.smartmenu.client.product.ProductRequest;
import com.smartmenu.client.product.ProductResponse;
import com.smartmenu.common.basemodel.mapper.BaseMapper;
import com.smartmenu.product.db.entity.Product;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<ProductRequest, Product, ProductResponse> {
}
