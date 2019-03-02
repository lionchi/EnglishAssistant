package org.gavrilov.mappers;

import org.gavrilov.domain.User;

public class UserCustomMapper {
    public String userAsString (User source) {
        return source.toString();
    }
}
