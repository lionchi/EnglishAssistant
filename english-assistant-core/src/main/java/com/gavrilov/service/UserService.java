package com.gavrilov.service;

import com.gavrilov.domain.User;
import com.gavrilov.domain.UserRole;
import com.gavrilov.repository.UserRepository;
import com.gavrilov.repository.UserRoleRepository;
import com.gavrilov.specification.UserSpecification;
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
