package com.example.habitstracker;

import com.example.habitstracker.dto.UserDTO;
import com.example.habitstracker.exceptions.HabitListNotFoundException;
import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.exceptions.UserNotFoundException;
import com.example.habitstracker.mappers.UserMapper;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.services.HabitListService;
import com.example.habitstracker.services.UserService;
import com.example.habitstracker.utils.DatabaseUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

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
    void contextLoads() {
    }

    @BeforeAll
    static void createUserDTO() {
        userDTO = new UserDTO();
        userDTO.setUsername("nick");
        userDTO.setPassword("123");
        userDTO.setHabitListName("name");
    }

    @AfterEach
    void tearDown() {
        DatabaseUtils.clear(jdbcTemplate);
    }

    @Test
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
    void checkUserDelete() {
        userService.addUser(userDTO);

        userService.deleteByUsername("nick");
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getByUsername("nick"));
    }

    @Test
    void checkExistenceHabitListGet() {
        userService.addUser(userDTO);
        HabitList habitList = userService.getUserHabitList(userDTO);
        Assertions.assertNotNull(habitList);
    }

    @Test
    void checkNotExistHabitList() {
        String name = "new_name";
        Assertions.assertThrows(HabitListNotFoundException.class, () -> habitListService.getListsWithName(name));
    }

}
