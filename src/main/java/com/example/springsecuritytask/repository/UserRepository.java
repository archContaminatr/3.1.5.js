package com.example.springsecuritytask.repository;

import com.example.springsecuritytask.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}