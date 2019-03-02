package com.gavrilov.specification;

import com.gavrilov.domain.Category;
import com.gavrilov.domain.Category_;
import org.springframework.data.jpa.domain.Specification;

public final class CategorySpecification {
    public static Specification<Category> findByNameCategory (final String nameCategory) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(Category_.nameCategory), nameCategory);
    }
}
