package org.gavrilov.mappers;

import fr.xebia.extras.selma.Selma;

@SuppressWarnings("uncheecked")
public class MapperFactory {
    public static <T> T createMapper(Class<T> typeMapperClass) {
        if (typeMapperClass == WorldMapper.class) {
            return (T) Selma.builder(WorldMapper.class).build();
        } else if (typeMapperClass == CategoryMapper.class) {
            return (T) Selma.builder(CategoryMapper.class).build();
        } else {
            return (T) Selma.builder(UserMapper.class).build();
        }
    }
}
