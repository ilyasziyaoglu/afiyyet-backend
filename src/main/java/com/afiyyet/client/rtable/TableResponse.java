package com.afiyyet.client.rtable;

import com.afiyyet.client.order.OrderResponse;
import com.afiyyet.common.basemodel.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TableResponse extends BaseResponse {
	private OrderResponse order;
	private String name;
	private String groupName;
	private Boolean isOpen;
}
