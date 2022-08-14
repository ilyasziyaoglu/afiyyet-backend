package com.afiyyet.common.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-06-29
 */

@Component
public class PriceUtils {

	public BigDecimal updatePriceByPercent(BigDecimal price, Double percent) {
		BigDecimal newPrice = price.multiply(BigDecimal.valueOf(1 + percent/100));
		BigDecimal diff = newPrice.remainder(BigDecimal.ONE);
		if (diff.compareTo(BigDecimal.valueOf(0.25)) < 0) {
			newPrice = newPrice.setScale(0, RoundingMode.FLOOR);
		} else if (diff.compareTo(BigDecimal.valueOf(0.25)) >= 0 && diff.compareTo(BigDecimal.valueOf(0.75)) < 0) {
			newPrice = newPrice.setScale(0, RoundingMode.FLOOR).add(BigDecimal.valueOf(0.5));
		} else {
			newPrice = newPrice.setScale(0, RoundingMode.CEILING);
		}
		return newPrice;
	}

	public BigDecimal applyDiscount(BigDecimal price, Double amount, boolean isPercent) {
		if (isPercent) {
			return updatePriceByPercent(price, amount);
		} else {
			return price.add(BigDecimal.valueOf(amount));
		}
	}
}
