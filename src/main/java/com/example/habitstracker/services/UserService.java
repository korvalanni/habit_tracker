package com.example.habitstracker.services;

import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.exceptions.UserNotFoundException;
import com.example.habitstracker.mappers.UserMapper;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.User;
import com.example.habitstracker.repository.HabitListRepository;
import com.example.habitstracker.repository.UserRepository;
import com.example.openapi.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    public User getByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty())
            throw new UserNotFoundException(username);
        return userOpt.get();
    }

    public User getById(long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty())
            throw new UserNotFoundException(id);
        return userOpt.get();
    }

    public void deleteByUsername(String username) {
        User user = getByUsername(username);
        userRepository.delete(user);
    }

    public void updateUserByUsername(String username, UserDTO userDTO) {
        User user = getByUsername(username);
        var userDTOName = userDTO.getUsername();
        var userDTOPassword = userDTO.getPassword();

        if (userDTOName != null && !username.equals(userDTOName)) {
            Optional<User> userOpt = userRepository.findByUsername(userDTOName);
            if (userOpt.isPresent())
                throw new UserExistException(userDTO.getUsername());
            user.setUsername(userDTOName);
        }

        if (userDTOPassword != null)
            user.setPassword(userDTOPassword);

        userRepository.save(user);
    }

    public HabitList getUserHabitList(UserDTO userDTO) {
        User user = getByUsername(userDTO.getUsername());
        return user.getHabitList();
    }
}
