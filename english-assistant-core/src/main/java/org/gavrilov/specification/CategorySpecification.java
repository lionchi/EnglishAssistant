package org.gavrilov.specification;

import org.gavrilov.domain.Category;
import org.gavrilov.domain.Category_;
import org.springframework.data.jpa.domain.Specification;

public final class CategorySpecification {
    public static Specification<Category> findByNameCategory (final String nameCategory) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(Category_.nameCategory), nameCategory);
    }
}
