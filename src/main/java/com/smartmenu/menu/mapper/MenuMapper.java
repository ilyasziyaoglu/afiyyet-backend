package com.smartmenu.menu.mapper;

import com.smartmenu.client.menu.MenuResponse;
import com.smartmenu.menu.model.Menu;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface MenuMapper {
	MenuResponse toResponse(Menu model);
}
