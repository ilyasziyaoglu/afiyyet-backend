package com.afiyyet.rtable.mapper;

import com.afiyyet.client.rtable.TableRequest;
import com.afiyyet.client.rtable.TableResponse;
import com.afiyyet.common.basemodel.mapper.BaseMapper;
import com.afiyyet.rtable.db.entity.RTable;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface TableMapper extends BaseMapper<TableRequest, RTable, TableResponse> {
}
