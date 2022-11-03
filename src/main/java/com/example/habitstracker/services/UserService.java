package com.example.habitstracker.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.exceptions.UserNotFoundException;
import com.example.habitstracker.mappers.UserMapper;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.User;
import com.example.habitstracker.repository.HabitListRepository;
import com.example.habitstracker.repository.UserRepository;
import com.example.openapi.dto.UserDTO;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final HabitListRepository habitListRepository;

    @Autowired
    public UserService(UserRepository userRepository, HabitListRepository habitListRepository) {
        this.userRepository = userRepository;
        this.habitListRepository = habitListRepository;
    }

    public User addUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        habitListRepository.save(user.getHabitList());
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent())
            throw new UserExistException(userDTO.getUsername());
        userRepository.save(user);
        return user;
    }

    public User getByUsername(String username){
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty())
            throw new UserNotFoundException(username);
        return userOpt.get();
    }

    public User getById(long id){
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty())
            throw new UserNotFoundException(id);
        return userOpt.get();
    }

    public void deleteByUsername(String username){
        User user = getByUsername(username);
        userRepository.delete(user);
    }

    // toDo будем переписывать
    public User updateUserPasswordByUsername(String username, UserDTO userDTO){
        User user = getByUsername(username);
        String password = userDTO.getPassword();
        user.setPassword(password);
        userRepository.save(user);
        return user;
    }

    public HabitList getUserHabitList(UserDTO userDTO){
        User user = getByUsername(userDTO.getUsername());
        return user.getHabitList();
    }
}
