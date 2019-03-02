package com.gavrilov.repository;

import com.gavrilov.domain.Category;
import com.gavrilov.domain.User;
import com.gavrilov.domain.World;
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

import java.util.List;
import java.util.Optional;

// Можно определить кэш для класса, тогда помечанные методы аннотацией Cacheable буду кэшироваться с указанным кэшем
// А можно было указать конкретный кэш для каждого метода, а также определить условия
@Repository
@CacheConfig(cacheNames = "worlds")
public interface WorldRepository extends JpaRepository<World, Long>, JpaSpecificationExecutor<World> {

    @CachePut
    Integer countWorldsByUser(User user);

    @Cacheable
    List<World> findByUser(User user);

    @Override
    Optional<World> findOne(Specification<World> specification);

    @Override
    void deleteById(Long aLong);

    @Modifying
    @Query("update World w set w.value = ?1, w.translation = ?2, w.category = ?3 where w.id = ?4")
    void updateWorld(String value, String translation, Category category, Long id);

    @CachePut
    @Override
    Page<World> findAll(Specification<World> specification, Pageable pageable);
}
