package com.smartmenu.category.mapper;

import com.smartmenu.category.db.entity.Category;
import com.smartmenu.client.category.CategoryRequest;
import com.smartmenu.client.category.CategoryResponse;
import com.smartmenu.common.basemodel.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper<CategoryRequest, Category, CategoryResponse> {
}
