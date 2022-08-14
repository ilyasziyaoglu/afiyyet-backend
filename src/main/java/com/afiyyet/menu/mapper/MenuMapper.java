package com.afiyyet.menu.mapper;

import com.afiyyet.client.menu.MenuResponse;
import com.afiyyet.menu.model.Menu;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface MenuMapper {
	MenuResponse toResponse(Menu model);
}
