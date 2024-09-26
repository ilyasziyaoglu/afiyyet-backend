package com.afiyyet.order.service;

import com.afiyyet.brand.db.entity.Brand_;
import com.afiyyet.client.order.OrderResponse;
import com.afiyyet.order.criteria.OrderCriteria;
import com.afiyyet.order.db.entity.Order;
import com.afiyyet.order.db.entity.Order_;
import com.afiyyet.order.db.repository.OrderRepository;
import com.afiyyet.order.mapper.OrderMapper;
import com.afiyyet.orderitem.db.entity.OrderItem_;
import com.afiyyet.rtable.db.entity.RTable_;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Order} entities in the database.
 * The main input is a {@link OrderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link OrderResponse} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrderQueryService extends QueryService<Order> {

    private static final Logger log = LoggerFactory.getLogger(OrderQueryService.class);

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public OrderQueryService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    /**
     * Return a {@link Page} of {@link OrderResponse} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderResponse> findByCriteria(OrderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Order> specification = createSpecification(criteria);
        return orderRepository.findAll(specification, page).map(orderMapper::toResponse);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Order> specification = createSpecification(criteria);
        return orderRepository.count(specification);
    }

    /**
     * Function to convert {@link OrderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Order> createSpecification(OrderCriteria criteria) {
        Specification<Order> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Order_.id));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Order_.createdDate));
            }
            if (criteria.getTotalPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPrice(), Order_.totalPrice));
            }
            if (criteria.getBrandId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getBrandId(), root -> root.join(Order_.brand, JoinType.LEFT).get(Brand_.id))
                );
            }
            if (criteria.getTableId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getTableId(), root -> root.join(Order_.table, JoinType.LEFT).get(RTable_.id))
                );
            }
            if (criteria.getOrderItemsId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getOrderItemsId(), root -> root.join(Order_.orderItems, JoinType.LEFT).get(OrderItem_.id))
                );
            }
        }
        return specification;
    }
}
