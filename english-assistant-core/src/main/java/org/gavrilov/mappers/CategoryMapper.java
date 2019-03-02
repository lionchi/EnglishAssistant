package org.gavrilov.mappers;

import org.gavrilov.domain.Category;
import org.gavrilov.dto.CategoryDTO;
import fr.xebia.extras.selma.Mapper;

@Mapper (withCustom = CategoryCustomMapper.class, withIgnoreFields = "worlds")
public interface CategoryMapper {
    CategoryDTO asCategoryDTO (Category category);

    Category asCategory (CategoryDTO soruce);
}
