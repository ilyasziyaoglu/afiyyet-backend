package com.smartmenu.rtable.mapper;

import com.smartmenu.client.rtable.TableRequest;
import com.smartmenu.client.rtable.TableResponse;
import com.smartmenu.common.basemodel.mapper.BaseMapper;
import com.smartmenu.rtable.db.entity.RTable;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface TableMapper extends BaseMapper<TableRequest, RTable, TableResponse> {
}
