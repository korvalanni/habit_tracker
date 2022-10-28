package com.example.habitstracker.services;

import com.example.habitstracker.dto.UserDTO;
import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.repository.HabitListRepository;
import com.example.habitstracker.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class UserServiceTest {


    /**
     * Добавление пользователя с одинаковым ником через Mock unittest
     */
    @Test
    void checkSameNicks() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        HabitListRepository habitListRepository = Mockito.mock(HabitListRepository.class);
        UserService userService = new UserService(userRepository, habitListRepository);

        UserDTO userDTO = new UserDTO();
        userDTO.setNickname("nick");
        userDTO.setPassword("123");
        userDTO.setHabitListName("name");

        Mockito.when(userRepository.findByNickname("nick")).thenReturn(Optional.empty());
        userService.addUser(userDTO);

        Mockito.when(userRepository.findByNickname("nick")).thenThrow(UserExistException.class);
        Assertions.assertThrows(UserExistException.class, () -> userService.addUser(userDTO));
    }
}
