package com.gavrilov.mappers;

import com.gavrilov.domain.Category;
import com.gavrilov.dto.CategoryDTO;
import fr.xebia.extras.selma.Mapper;

@Mapper (withCustom = CategoryCustomMapper.class, withIgnoreFields = "worlds")
public interface CategoryMapper {
    CategoryDTO asCategoryDTO (Category category);

    Category asCategory (CategoryDTO soruce);
}
