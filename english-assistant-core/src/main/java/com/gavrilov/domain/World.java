package com.gavrilov.domain;

import com.gavrilov.dto.CategoryDTO;
import com.gavrilov.dto.WorldDTO;
import com.gavrilov.mappers.CategoryMapper;
import com.gavrilov.mappers.MapperFactory;
import com.gavrilov.mappers.WorldMapper;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name="world")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class World extends com.gavrilov.domain.Entity {
    private final static WorldMapper mapperWorld = MapperFactory.createMapper(WorldMapper.class);
    private final static CategoryMapper mapperCategory = MapperFactory.createMapper(CategoryMapper.class);

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "translation", nullable = false)
    private String translation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public World() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public static WorldDTO getWorldDTO(World world) {
        CategoryDTO categoryDTO = mapperCategory.asCategoryDTO(world.getCategory());
        return mapperWorld.asWorldDTO(world);
    }
}
