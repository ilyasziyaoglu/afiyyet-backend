package com.afiyyet.order.utils;

import com.afiyyet.order.db.entity.Order;
import com.afiyyet.orderitem.db.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-11-22
 */

@Component
public class OrderUtils {
	public void updateTotalPrice(Order order) {
		order.setTotalPrice(order.getOrderItems()
				.stream()
				.map(OrderItem::getTotalPrice)
				.filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add));
	}
}
