package com.example.springsecuritytask.service;

import com.example.springsecuritytask.entities.Role;
import com.example.springsecuritytask.entities.User;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface UserService {
    void saveUser(User user);

    void deleteById(Long id);

    List<User> findAllUsers();

    List<Role> findAllRoles();

    User getUserById(Long id);

    User findUserByEmail(String email);

}