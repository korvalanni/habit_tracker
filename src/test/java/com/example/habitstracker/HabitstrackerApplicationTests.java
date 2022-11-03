package com.example.habitstracker;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.habitstracker.exceptions.HabitListNotFoundException;
import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.exceptions.UserNotFoundException;
import com.example.habitstracker.mappers.UserMapper;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.services.HabitListService;
import com.example.habitstracker.services.UserService;
import com.example.habitstracker.utils.DatabaseUtils;
import com.example.openapi.dto.UserDTO;

@SpringBootTest
class HabitstrackerApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private HabitListService habitListService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private UserMapper userMapper;

    private static UserDTO userDTO;

    @Test
    @Disabled
    void contextLoads() {
    }

    @BeforeAll
    static void createUserDTO() {
        userDTO = new UserDTO();
        userDTO.setUsername("nick");
        userDTO.setPassword("123");
        userDTO.setHabitListName("name");
    }

    @BeforeEach
    void tearDown() {
        DatabaseUtils.clear(jdbcTemplate);
    }

    @Test
//    @Disabled
    void checkSameNicks() {
        userService.addUser(userDTO);
        Assertions.assertThrows(UserExistException.class, () -> userService.addUser(userDTO));
    }

    /**
     * 1) Создаем пользователя и сохраняем его
     * 2) Создаем новую dto с новым паролем и через сервис меняем пароль
     * 3) Проверям, что в бд лежит пользователь с новым паролем
     */
    @Test
//    @Disabled
    void checkUpdate() {
        String username = "nick";
        String newPassword = "234";
        // Создаем пользователя и сохраняем его
        userService.addUser(userDTO);

        // Создаем новую dto с новым паролем и через сервис меняем пароль
        UserDTO userDTO1 = new UserDTO();
        userDTO1.setPassword(newPassword);

        // Создаем новую dto с новым паролем и через сервис меняем пароль
        userService.updateUserPasswordByUsername(username, userDTO1);

        //Проверям, что в бд лежит пользователь с новым паролем
        String password = userService.getByUsername(username).getPassword();
        Assertions.assertEquals(password, newPassword);
    }

    @Test
//    @Disabled
    void checkUserDelete() {
        userService.addUser(userDTO);

        userService.deleteByUsername("nick");
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getByUsername("nick"));
    }

    @Test
//    @Disabled
    void checkExistenceHabitListGet() {
        userService.addUser(userDTO);
        HabitList habitList = userService.getUserHabitList(userDTO);
        Assertions.assertNotNull(habitList);
    }

    @Test
//    @Disabled
    void checkNotExistHabitList() {
        String name = "new_name";
        Assertions.assertThrows(HabitListNotFoundException.class, () -> habitListService.getListsWithName(name));
    }

}
