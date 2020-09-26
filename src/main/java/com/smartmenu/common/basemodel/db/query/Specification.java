//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.smartmenu.common.basemodel.db.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Predicate;

public interface Specification<AbstractEntity> {
    Predicate toPredicate(Root<AbstractEntity> var1, CriteriaBuilder var2) throws Exception;

    Class<AbstractEntity> getType();

    org.springframework.data.jpa.domain.Specification<AbstractEntity> idEqual(Long var1);

    org.springframework.data.jpa.domain.Specification<AbstractEntity> idsEqual(List<Long> var1);
}
