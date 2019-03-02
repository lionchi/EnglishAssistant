package com.gavrilov.specification;

import com.gavrilov.domain.User;
import com.gavrilov.domain.User_;
import com.gavrilov.domain.World;
import com.gavrilov.domain.World_;
import org.springframework.data.jpa.domain.Specification;

public final class WorldSpecification {
    public static Specification<World> findByValue(final String value) {
        return (r, cq, cb) -> cb.like(r.get(World_.value), value);
    }

    public static Specification<World> findAllByUser(final User user) {
        return (r, cq, cb) -> cb.equal(r.get(World_.user).get(User_.id), user.getId());
    }
}
