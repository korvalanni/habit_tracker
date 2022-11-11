package com.example.habitstracker.models.listeners;

import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.habitstracker.models.UserEntity;

// @Component
public class UserListener {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserListener(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PrePersist
    // TODO проверить, что при обновлении этот шифровщик пароля работает @PreUpdate
    public void hashPassword(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
