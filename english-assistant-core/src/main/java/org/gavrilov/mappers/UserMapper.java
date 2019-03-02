package org.gavrilov.mappers;

import org.gavrilov.domain.User;
import org.gavrilov.dto.UserDTO;
import fr.xebia.extras.selma.Field;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;

@Mapper(withCustom = UserCustomMapper.class, withCustomFields = {@Field({"userRole.id", "userRoleId"})},
        withIgnoreMissing = IgnoreMissing.ALL, withIgnoreFields = "worlds")
public interface UserMapper {
    UserDTO asUserDTO (User user);

    User asUser (UserDTO source);
}
