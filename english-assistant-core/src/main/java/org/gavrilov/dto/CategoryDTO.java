package org.gavrilov.dto;

import javax.validation.constraints.NotEmpty;

public class CategoryDTO {
    private Long id;

    @NotEmpty(message = "Это поле недолжно быть пустым")
    private String nameCategory;

    public CategoryDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    @Override
    public String toString() {
        return nameCategory;
    }
}
