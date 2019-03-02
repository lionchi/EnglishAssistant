package com.gavrilov.domain;

import com.gavrilov.dto.CategoryDTO;
import com.gavrilov.mappers.CategoryMapper;
import com.gavrilov.mappers.MapperFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category extends com.gavrilov.domain.Entity {
    private final static CategoryMapper mapperCategory = MapperFactory.createMapper(CategoryMapper.class);

    @Column(name = "name_category")
    private String nameCategory;

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(mappedBy = "category", cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<World> worlds;

    public Category() {
    }

    @Nonnull
    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<World> getWorlds() {
        return worlds;
    }

    public void setWorlds(List<World> worlds) {
        this.worlds = worlds;
    }

    public static CategoryDTO getCategoryDTO (Category category) {
        return mapperCategory.asCategoryDTO(category);
    }
}
