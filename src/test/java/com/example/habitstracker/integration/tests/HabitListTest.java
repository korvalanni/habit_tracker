package com.example.habitstracker.integration.tests;

import com.example.habitstracker.integration.utils.TestUserBuilder;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
import com.example.habitstracker.services.HabitListService;
import com.example.habitstracker.services.MapperService;
import com.example.openapi.dto.Color;
import com.example.openapi.dto.Priority;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HabitListTest extends AbstractIntegrationTest {
    @Autowired
    private HabitListService habitListService;
    @Autowired
    private MapperService mapperService;
    private Habit habit;
    private UserEntity user;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        super.setup();

        user = new TestUserBuilder().build();
        habit = new Habit("Test", "Description", Priority.HIGH, Color.GREEN, 1L, List.of(1L, 2L));

        authDSL.register(user);
        authDSL.login(user);
    }

    @Test
    void test_getHabitList() throws JsonProcessingException{
       var authHabitList =  new HabitList();
       mapperService.transform(habitListDSL.getHabitList(), authHabitList);
        System.out.println("имя пользователя");
        System.out.println(authHabitList.getName());
        var expectedHabitList = habitListService.getHabitListWithId(user.getHabitList().getId());
        System.out.println(expectedHabitList.getName());
       //System.out.println(expectedHabitList.getName());
    }


}
