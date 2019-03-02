package com.gavrilov.mappers;

import com.gavrilov.domain.World;

public class WorldCustomMapper {
    public String worldAsString (World source) {
        return source.toString();
    }
}
