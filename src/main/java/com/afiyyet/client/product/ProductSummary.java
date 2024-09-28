package com.afiyyet.client.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummary {
	private String name;
	private Double count = 0.0;
	private Double total = 0.0;
}
