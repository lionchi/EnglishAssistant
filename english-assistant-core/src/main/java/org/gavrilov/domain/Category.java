package org.gavrilov.domain;

import org.gavrilov.dto.CategoryDTO;
import org.gavrilov.mappers.CategoryMapper;
import org.gavrilov.mappers.MapperFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category extends org.gavrilov.domain.Entity {
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
