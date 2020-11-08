package com.smartmenu.order.db.repository;

import com.smartmenu.common.basemodel.db.repository.BaseRepository;
import com.smartmenu.order.db.entity.Order;
import org.springframework.stereotype.Repository;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface OrderRepository extends BaseRepository<Order> {
}
