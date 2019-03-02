package org.gavrilov.specification;

import org.gavrilov.domain.User;
import org.gavrilov.domain.User_;
import org.gavrilov.domain.World;
import org.gavrilov.domain.World_;
import org.springframework.data.jpa.domain.Specification;

public final class WorldSpecification {
    public static Specification<World> findByValue(final String value) {
        return (r, cq, cb) -> cb.like(r.get(World_.value), value);
    }

    public static Specification<World> findAllByUser(final User user) {
        return (r, cq, cb) -> cb.equal(r.get(World_.user).get(User_.id), user.getId());
    }
}
