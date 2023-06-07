package com.example.springsecuritytask.config;

import com.example.springsecuritytask.entities.Role;
import com.example.springsecuritytask.entities.User;
import com.example.springsecuritytask.service.UserServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class DbInit {

    private UserServiceImpl userServiceImpl;

    @Autowired
    public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostConstruct
    private void postConstruct() {
        Role ROLE_ADMIN = new Role(1L, "ROLE_ADMIN");
        Role ROLE_USER = new Role(2L,"ROLE_USER");
        User admin = new User(1L, "admin@mail.com", "admin", "admin",
                "admin", new HashSet<>(Arrays.asList(ROLE_ADMIN, ROLE_USER)));
        User user = new User(2L, "user@mail.com", "user", "user",
                "user", new HashSet<>(List.of(ROLE_USER)));
        if (userServiceImpl.findUserByEmail(admin.getEmail()) == null) {
            userServiceImpl.saveUser(admin);
        }
        if (userServiceImpl.findUserByEmail(user.getEmail()) == null) {
            userServiceImpl.saveUser(user);
        }
    }

}
