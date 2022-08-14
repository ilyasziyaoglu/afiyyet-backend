package com.afiyyet.common.basemodel.request.pager;

import com.afiyyet.common.basemodel.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PageDto<T> extends BaseRequest {

	private static final long serialVersionUID = 1L;

	private int draw = 1;

	private int size = 10;

	private int page = 0;

	private T filterDto;

	private List<DtColumnDto> columns;

	private List<DtOrderDto> order;

	private DtSearchDto search;

}
