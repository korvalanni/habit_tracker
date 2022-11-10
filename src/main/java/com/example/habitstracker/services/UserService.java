package com.example.habitstracker.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.exceptions.UserNotFoundException;
import com.example.habitstracker.mappers.UserMapper;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
import com.example.habitstracker.repository.UserRepository;
import com.example.openapi.dto.UserDTO;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final HabitListService habitListService;

    @Autowired
    public UserService(UserRepository userRepository, HabitListService habitListService) {
        this.userRepository = userRepository;
        this.habitListService = habitListService;
    }

    public UserEntity addUser(UserDTO userDTO) {
        UserEntity user = UserMapper.toEntity(userDTO);
        habitListService.addHabitList(user.getHabitList());
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent())
            throw new UserExistException(userDTO.getUsername());
        userRepository.save(user);
        return user;
    }

    public UserEntity getByUsername(String username){
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty())
            throw new UserNotFoundException(username);
        return userOpt.get();
    }

    public UserEntity getById(long id){
        Optional<UserEntity> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty())
            throw new UserNotFoundException(id);
        return userOpt.get();
    }

    public void deleteByUsername(String username){
        UserEntity user = getByUsername(username);
        userRepository.delete(user);
    }

    public UserEntity updateUserPasswordByUsername(String username, UserDTO userDTO){
        UserEntity user = getByUsername(username);
        String password = userDTO.getPassword();
        user.setPassword(password);
        userRepository.save(user);
        return user;
    }

    public HabitList getUserHabitList(UserDTO userDTO){
        UserEntity user = getByUsername(userDTO.getUsername());
        return user.getHabitList();
    }
}
