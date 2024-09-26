package com.afiyyet.order.criteria;

import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.ZonedDateTimeFilter;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Order} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.OrderResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter createdDate;

    private BigDecimalFilter totalPrice;

    private LongFilter brandId;

    private LongFilter tableId;

    private LongFilter orderItemsId;

    private Boolean distinct;

    public OrderCriteria() {}

    public OrderCriteria(OrderCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(ZonedDateTimeFilter::copy).orElse(null);
        this.totalPrice = other.optionalTotalPrice().map(BigDecimalFilter::copy).orElse(null);
        this.brandId = other.optionalBrandId().map(LongFilter::copy).orElse(null);
        this.tableId = other.optionalTableId().map(LongFilter::copy).orElse(null);
        this.orderItemsId = other.optionalOrderItemsId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public OrderCriteria copy() {
        return new OrderCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getCreatedDate() {
        return createdDate;
    }

    public Optional<ZonedDateTimeFilter> optionalCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    public ZonedDateTimeFilter createdDate() {
        if (createdDate == null) {
            setCreatedDate(new ZonedDateTimeFilter());
        }
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTimeFilter createdDate) {
        this.createdDate = createdDate;
    }

    public BigDecimalFilter getTotalPrice() {
        return totalPrice;
    }

    public Optional<BigDecimalFilter> optionalTotalPrice() {
        return Optional.ofNullable(totalPrice);
    }

    public BigDecimalFilter totalPrice() {
        if (totalPrice == null) {
            setTotalPrice(new BigDecimalFilter());
        }
        return totalPrice;
    }

    public void setTotalPrice(BigDecimalFilter totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LongFilter getBrandId() {
        return brandId;
    }

    public Optional<LongFilter> optionalBrandId() {
        return Optional.ofNullable(brandId);
    }

    public LongFilter brandId() {
        if (brandId == null) {
            setBrandId(new LongFilter());
        }
        return brandId;
    }

    public void setBrandId(LongFilter brandId) {
        this.brandId = brandId;
    }

    public LongFilter getTableId() {
        return tableId;
    }

    public Optional<LongFilter> optionalTableId() {
        return Optional.ofNullable(tableId);
    }

    public LongFilter tableId() {
        if (tableId == null) {
            setTableId(new LongFilter());
        }
        return tableId;
    }

    public void setTableId(LongFilter tableId) {
        this.tableId = tableId;
    }

    public LongFilter getOrderItemsId() {
        return orderItemsId;
    }

    public Optional<LongFilter> optionalOrderItemsId() {
        return Optional.ofNullable(orderItemsId);
    }

    public LongFilter orderItemsId() {
        if (orderItemsId == null) {
            setOrderItemsId(new LongFilter());
        }
        return orderItemsId;
    }

    public void setOrderItemsId(LongFilter orderItemsId) {
        this.orderItemsId = orderItemsId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderCriteria that = (OrderCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(totalPrice, that.totalPrice) &&
            Objects.equals(brandId, that.brandId) &&
            Objects.equals(tableId, that.tableId) &&
            Objects.equals(orderItemsId, that.orderItemsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdDate, totalPrice, brandId, tableId, orderItemsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalTotalPrice().map(f -> "totalPrice=" + f + ", ").orElse("") +
            optionalBrandId().map(f -> "brandId=" + f + ", ").orElse("") +
            optionalTableId().map(f -> "tableId=" + f + ", ").orElse("") +
            optionalOrderItemsId().map(f -> "orderItemsId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
