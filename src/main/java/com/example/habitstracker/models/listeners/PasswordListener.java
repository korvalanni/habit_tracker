package com.example.habitstracker.models.listeners;

import com.example.habitstracker.models.UserPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.PreUpdate;

public class PasswordListener {
    private final PasswordEncoder passwordEncoder;

    // todo: Do you really need this annotation?
    @Autowired
    public PasswordListener(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PreUpdate
    public void hashPassword(UserPassword user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
