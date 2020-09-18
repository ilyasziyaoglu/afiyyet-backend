package com.smartmenu.client.product;

import com.smartmenu.common.basemodel.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ArrangeProductRequest extends BaseRequest {
	Map<Long, Integer> arrangement = new HashMap<>();
}
