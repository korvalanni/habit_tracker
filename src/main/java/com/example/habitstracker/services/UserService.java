package com.example.habitstracker.services;

import com.example.habitstracker.dto.UserDTO;
import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.exceptions.UserNotFoundException;
import com.example.habitstracker.mappers.UserMapper;
import com.example.habitstracker.models.User;
import com.example.habitstracker.repository.HabitListRepository;
import com.example.habitstracker.repository.UserRepository;
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
        if(userRepository.findByNickname(userDTO.getNickname()).isPresent())
            throw new UserExistException(userDTO.getNickname());
        userRepository.save(user);
        return user;
    }

    public User getByNickname(String nickname){
        Optional<User> userOpt = userRepository.findByNickname(nickname);
        if (userOpt.isEmpty())
            throw new UserNotFoundException(nickname);
        return userOpt.get();
    }

    public void deleteByNickName(String nickname){
        User user = getByNickname(nickname);
        userRepository.delete(user);
    }
    
    // toDo будем переписывать
    public User updateUserPasswordByNickName(String nickname, UserDTO userDTO){
        User user = getByNickname(nickname);
        String password = userDTO.getPassword();
        user.setPassword(password);
        userRepository.save(user);
        return user;
    }
}
