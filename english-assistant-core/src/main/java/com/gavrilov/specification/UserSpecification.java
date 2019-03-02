package com.gavrilov.specification;

import com.gavrilov.domain.User;
import com.gavrilov.domain.User_;
import org.springframework.data.jpa.domain.Specification;

public final class UserSpecification {
    public static Specification<User> findUserByLogin(final String login) {
        return (r, cq, cb) -> cb.equal(r.get(User_.login), login);
    }
}
