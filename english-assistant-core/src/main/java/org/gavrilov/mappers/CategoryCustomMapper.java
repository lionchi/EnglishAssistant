package org.gavrilov.mappers;

import org.gavrilov.domain.Category;

public class CategoryCustomMapper {
    public String categoryAsString (Category source) {
        return source.toString();
    }
}
