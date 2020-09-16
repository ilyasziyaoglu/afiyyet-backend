//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.smartmenu.common.basemodel.db.query;

import com.google.common.base.Predicate;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

public abstract class AbstractSpecification<AbstractEntity, FilterDto> implements Specification<AbstractEntity> {
    public AbstractSpecification() {
    }

    public org.springframework.data.jpa.domain.Specification<AbstractEntity> filter(FilterDto dto) {
        return null;
    }

    public Class<AbstractEntity> getType() {
        ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
        return (Class)type.getActualTypeArguments()[0];
    }

    public Predicate toPredicate(Root<AbstractEntity> root, CriteriaBuilder cb) throws Exception {
        throw new Exception("NotImplementedException");
    }

    public org.springframework.data.jpa.domain.Specification<AbstractEntity> idEqual(Long id) {
        return (root, criteria, cb) -> {
            return cb.equal(root.get("identifier"), id);
        };
    }

    public org.springframework.data.jpa.domain.Specification<AbstractEntity> idsEqual(List<Long> ids) {
        return (root, criteria, cb) -> {
            return root.get("identifier").in(ids);
        };
    }
}
