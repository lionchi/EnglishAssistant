package com.gavrilov.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class WorldDTO {
    private Long id;

    @NotEmpty(message = "Это поле недолжно быть пустым")
    private String value;

    @NotEmpty(message = "Это поле недолжно быть пустым")
    private String translation;

    private Long userId;

    @NotNull (message = "Это поле недолжно быть пустым")
    private Long categoryId;

    private String nameCategory;

    public WorldDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }
}
