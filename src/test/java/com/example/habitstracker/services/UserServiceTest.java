package com.example.habitstracker.services;

import com.example.habitstracker.dto.UserDTO;
import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.repository.HabitListRepository;
import com.example.habitstracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {


    /**
     * Добавление пользователя с одинаковым ником
     * Unit test -- проверка работы функции addUser
     */
    @Test
    void checkSameNicks() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = Mockito.mock(UserService.class);
        HabitListRepository habitListRepository = Mockito.mock(HabitListRepository.class);
        final UserDTO userDTO = new UserDTO();
        userDTO.setNickname("nick");
        userDTO.setPassword("123");
        userDTO.setHabitListName("name");

        Mockito.when(userService.addUser(userDTO)).thenCallRealMethod();
        Mockito.when(userService.addUser(userDTO)).thenThrow(UserExistException.class);


    }
}
