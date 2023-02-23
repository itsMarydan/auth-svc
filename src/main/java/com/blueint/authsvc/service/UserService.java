package com.blueint.authsvc.service;

import com.blueint.authsvc.model.Role;
import com.blueint.authsvc.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    Role getRole(String roleName);
    List<User> getUsers();

}


// TODO: Page Return List USERS SO THAT WE CAN SEE THE USERS IN THE DATABASE WITHOUT LOADING ALL THE USERS AT ONCE