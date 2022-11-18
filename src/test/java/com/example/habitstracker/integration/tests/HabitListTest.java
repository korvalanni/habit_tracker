package com.example.habitstracker.integration.tests;

import com.example.habitstracker.integration.utils.TestUserBuilder;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
import com.example.habitstracker.services.HabitListService;
import com.example.habitstracker.services.MapperService;
import com.example.habitstracker.services.UserService;
import com.example.openapi.dto.Color;
import com.example.openapi.dto.Priority;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HabitListTest extends AbstractIntegrationTest {
    @Autowired
    private HabitListService habitListService;

    @Autowired
    private MapperService mapperService;

    @Autowired
    private UserService userService;

    private HabitList habitList;


    @BeforeEach
    void setUp() throws JsonProcessingException {
        UserEntity user = new TestUserBuilder().build();
        var habit1 = new Habit("Test1", "Description1", Priority.HIGH,
                Color.GREEN, 1L, List.of(1L, 2L));
        var habit2 = new Habit("Test2", "Description2", Priority.MIDDLE, Color.YELLOW,
                3L, List.of(1L, 2L));

        authDSL.register(user);
        authDSL.login(user);

        habitDSL.createHabit(habit1);
        habitDSL.createHabit(habit2);

    }

    // @Disabled("FIX ME не находится пользователь с 0 id хотя мы его создаеи")
    @Test
    void test_updateHabitList() throws JsonProcessingException {
        var id = userService.getById(0).getHabitList().getId();
        var habitList = userService.getById(0).getHabitList();
        var updatedName = "new";
        habitList.setName(updatedName);
        habitListDSL.updateHabitList(id, habitList);
        var existedName = userService.getById(0).getHabitList().getName();

        Assertions.assertEquals(updatedName, existedName);


    }

    @Test
    void test_getHabitList() throws JsonProcessingException{
        var expectedHabitList = userService.getById(0).getHabitList();
        var existedHabitList = habitListDSL.getHabitList();

        Assertions.assertEquals(expectedHabitList, existedHabitList);
    }
}
