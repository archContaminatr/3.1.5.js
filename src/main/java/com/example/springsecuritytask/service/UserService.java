package com.example.springsecuritytask.service;

import com.example.springsecuritytask.entities.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    User findUserByEmail(String email);

    List<User> findAllUsers();
}