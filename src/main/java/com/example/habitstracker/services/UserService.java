package com.example.habitstracker.services;

import com.example.habitstracker.dto.UserDTO;
import com.example.habitstracker.mappers.UserMapper;
import com.example.habitstracker.models.User;
import com.example.habitstracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        userRepository.save(user);
        return user;
    }

}
