package com.afiyyet.orderitem.db.repository;

import com.afiyyet.common.basemodel.db.repository.BaseRepository;
import com.afiyyet.orderitem.db.entity.OrderItem;
import org.springframework.stereotype.Repository;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface OrderItemRepository extends BaseRepository<OrderItem> {
}
