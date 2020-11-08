package com.smartmenu.rtable.mapper;

import com.smartmenu.client.rtable.RTableRequest;
import com.smartmenu.client.rtable.RTableResponse;
import com.smartmenu.common.basemodel.mapper.BaseMapper;
import com.smartmenu.rtable.db.entity.RTable;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface TableMapper extends BaseMapper<RTableRequest, RTable, RTableResponse> {
}
