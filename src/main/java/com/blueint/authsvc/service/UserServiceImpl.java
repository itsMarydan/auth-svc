package com.blueint.authsvc.service;

import com.blueint.authsvc.component.UserDefaults;
import com.blueint.authsvc.model.Role;
import com.blueint.authsvc.model.User;
import com.blueint.authsvc.repository.RoleRepository;
import com.blueint.authsvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDefaults userDefaults;

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getName());
        log.info("Adding default values for locked {} and active {}", userDefaults.isDefaultUserStateLocked(), userDefaults.isDefaultUserStateActive());
        if (!user.getLocked()) {
            user.setLocked(userDefaults.isDefaultUserStateLocked());
        }
        if (!user.getActive()) {
            user.setActive(userDefaults.isDefaultUserStateActive());
        }
//        TODO: Add user validation here
        return userRepository.save(user);
    }


    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }


    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public Role getRole(String roleName) {
        log.info("Fetching role {}", roleName);
        return roleRepository.findByName(roleName);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

}
