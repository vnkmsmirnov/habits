package com.tracker.habits.services;

import com.tracker.habits.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserRepository extends UserDetailsService {
    User findByUsername(String username);
}