package com.example.habitstracker.models.listeners;

import com.example.habitstracker.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.PrePersist;

public class UserListener {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserListener(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PrePersist
    public void hashPassword(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
