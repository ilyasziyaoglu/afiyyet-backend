package com.afiyyet.client.order;

import com.afiyyet.client.product.ProductSummary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummary {
	private Long count = 0L;
	private Double total = 0.0;
	private Double min = 0.0;
	private Double avg = 0.0;
	private Double max = 0.0;
	private Map<ZonedDateTime, List<Double>> orderTotals;
	private List<ProductSummary> productSummaries = new ArrayList<>();
}
