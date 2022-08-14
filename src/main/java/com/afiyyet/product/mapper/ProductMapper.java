package com.afiyyet.product.mapper;

import com.afiyyet.client.product.ProductRequest;
import com.afiyyet.client.product.ProductResponse;
import com.afiyyet.common.basemodel.mapper.BaseMapper;
import com.afiyyet.product.db.entity.Product;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<ProductRequest, Product, ProductResponse> {
}
