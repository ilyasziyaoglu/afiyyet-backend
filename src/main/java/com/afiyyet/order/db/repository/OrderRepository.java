package com.afiyyet.order.db.repository;

import com.afiyyet.common.basemodel.db.repository.BaseRepository;
import com.afiyyet.order.db.entity.Order;
import org.springframework.stereotype.Repository;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface OrderRepository extends BaseRepository<Order> {
}
