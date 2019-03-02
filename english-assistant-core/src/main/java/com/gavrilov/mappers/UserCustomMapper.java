package com.gavrilov.mappers;

import com.gavrilov.domain.User;

public class UserCustomMapper {
    public String userAsString (User source) {
        return source.toString();
    }
}
