package com.example.habitstracker.models.listeners;

import com.example.habitstracker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.PrePersist;

// @Component
public class UserListener {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserListener(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PrePersist
    // TODO проверить, что при обновлении этот шифровщик пароля работает @PreUpdate
    public void hashPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
