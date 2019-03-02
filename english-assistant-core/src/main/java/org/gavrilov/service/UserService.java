package org.gavrilov.service;

import org.gavrilov.domain.User;
import org.gavrilov.domain.UserRole;
import org.gavrilov.repository.UserRepository;
import org.gavrilov.repository.UserRoleRepository;
import org.gavrilov.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    public void saveUser(User user) {
        user.setEnabled(1);
        UserRole admin = userRoleRepository.findByRolename("USER");
        user.setUserRole(admin);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findUserByLogin(String login) {
        Optional<User> optionalUser = userRepository.findOne(UserSpecification.findUserByLogin(login));
        return optionalUser.orElse(null);
    }
}
