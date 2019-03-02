package org.gavrilov.repository;

import org.gavrilov.domain.Category;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "categories")
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    @Override
    @CachePut
    Page<Category> findAll(Pageable pageable);

    @Override
    @Cacheable
    Optional<Category> findOne(Specification<Category> specification);

    @Modifying
    @Query("update Category c set c.nameCategory = ?1 where c.id = ?2")
    void updateCategory (String newNameCategory, Long id);
}
