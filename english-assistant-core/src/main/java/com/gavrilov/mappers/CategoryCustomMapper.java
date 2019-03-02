package com.gavrilov.mappers;

import com.gavrilov.domain.Category;

public class CategoryCustomMapper {
    public String categoryAsString (Category source) {
        return source.toString();
    }
}
