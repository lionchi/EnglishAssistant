package org.gavrilov.mappers;

import org.gavrilov.domain.World;

public class WorldCustomMapper {
    public String worldAsString (World source) {
        return source.toString();
    }
}
