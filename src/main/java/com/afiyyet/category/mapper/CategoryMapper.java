package com.afiyyet.category.mapper;

import com.afiyyet.category.db.entity.Category;
import com.afiyyet.client.category.CategoryRequest;
import com.afiyyet.client.category.CategoryResponse;
import com.afiyyet.common.basemodel.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper<CategoryRequest, Category, CategoryResponse> {
}
