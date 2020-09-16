package com.smartmenu.common.basemodel.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

public abstract class AbstractBaseEntity implements Serializable {
    private static final long serialVersionUID = 6204877037070575919L;

    public abstract Long getId();

    public AbstractBaseEntity() {
    }

    public AbstractBaseEntity(ZonedDateTime createdDate, ZonedDateTime lastModifiedDate, String createdBy, String lastModifiedBy) {
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
    }

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    private ZonedDateTime lastModifiedDate = ZonedDateTime.now();

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
}
