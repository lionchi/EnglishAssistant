package org.gavrilov.mappers;

import org.gavrilov.domain.World;
import org.gavrilov.dto.WorldDTO;
import fr.xebia.extras.selma.Field;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;

@Mapper(withCustom = UserCustomMapper.class, withCustomFields = {@Field({"user.id", "userId"}),
        @Field({"category.id", "categoryId"}), @Field({"category.nameCategory", "nameCategory"}),},
        withIgnoreMissing = IgnoreMissing.ALL, withIgnoreFields = "worlds")
public interface WorldMapper {
    WorldDTO asWorldDTO (World world);

    World asWorld (WorldDTO source);
}
