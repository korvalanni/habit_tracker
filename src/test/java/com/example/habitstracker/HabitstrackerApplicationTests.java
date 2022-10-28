package com.example.habitstracker;

import com.example.habitstracker.dto.UserDTO;
import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

@SpringBootTest
// @RunWith(SpringJUnit4ClassRunner.class)
class HabitstrackerApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
    }

    @AfterEach
    void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users", "habit", "habit_list");
    }


    @Test
    void checkSameNicks() {
        
        UserDTO userDTO = new UserDTO();
        userDTO.setNickname("nick");
        userDTO.setPassword("123");
        userDTO.setHabitListName("name");

        userService.addUser(userDTO);
        Assertions.assertThrows(UserExistException.class, () -> userService.addUser(userDTO));
    }
}
