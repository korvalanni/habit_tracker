package com.example.habitstracker.models.listeners;

import com.example.habitstracker.models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.PrePersist;

public class UserListener {
    @PrePersist
    public void hashPassword(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    }
}
