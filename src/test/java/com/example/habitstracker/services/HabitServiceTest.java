package com.example.habitstracker.services;

import com.example.habitstracker.dsl.UserDSL;
import com.example.habitstracker.dto.UserDTO;
import com.example.habitstracker.models.*;
import com.example.habitstracker.repository.HabitListRepository;
import com.example.habitstracker.repository.HabitRepository;
import com.example.habitstracker.repository.UserRepository;
import com.example.habitstracker.security.UserCredentials;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

public class HabitServiceTest {
    @Test
    void test_addHabit() {
        UserService userService = Mockito.mock(UserService.class);
        HabitRepository habitRepository = Mockito.mock(HabitRepository.class);
        HabitService habitService = new HabitService(userService, habitRepository);

        User user = new User();
        user.setPassword("123");
        user.setHabitList(new HabitList());
        user.setUsername("user");
        user.setUserId(0L);

        UserCredentials userCredentials = new UserCredentials(0, "nick");

        Habit habit = new Habit();
        habit.setHabitList(null);
        habit.setId(0L);
        habit.setRepeats(0L);
        habit.setColor(Color.GREEN);
        habit.setPriority(Priority.HIGH);
        habit.setDescription("Test");
        habit.setTitle("Title");

        Mockito.when(userService.getById(0)).thenReturn(user);

        habitService.addHabit(userCredentials, habit);

        Mockito.verify(habitRepository, Mockito.times(1)).save(habit);
    }

    @Test
    void test_deleteHabit() {
        UserService userService = Mockito.mock(UserService.class);
        HabitRepository habitRepository = Mockito.mock(HabitRepository.class);
        HabitService habitService = new HabitService(userService, habitRepository);

        User user = new User();
        user.setPassword("123");
        user.setHabitList(new HabitList());
        user.setUsername("user");
        user.setUserId(0L);

        UserCredentials userCredentials = new UserCredentials(0, "nick");

        Habit habit = new Habit();
        habit.setHabitList(null);
        habit.setId(0L);
        habit.setRepeats(0L);
        habit.setColor(Color.GREEN);
        habit.setPriority(Priority.HIGH);
        habit.setDescription("Test");
        habit.setTitle("Title");

        Mockito.when(userService.getById(0)).thenReturn(user);

        habitService.addHabit(userCredentials, habit);
        habitService.deleteHabit(0L);

        Mockito.verify(habitRepository, Mockito.times(1)).save(habit);
        Mockito.verify(habitRepository, Mockito.times(1)).deleteById(0L);
    }

    @Test
    void test_updateHabit() {
        UserService userService = Mockito.mock(UserService.class);
        HabitRepository habitRepository = Mockito.mock(HabitRepository.class);
        HabitService habitService = new HabitService(userService, habitRepository);

        User user = new User();
        user.setPassword("123");
        user.setHabitList(new HabitList());
        user.setUsername("user");
        user.setUserId(0L);

        UserCredentials userCredentials = new UserCredentials(0, "nick");

        Habit habit = new Habit();
        habit.setHabitList(null);
        habit.setId(0L);
        habit.setRepeats(0L);
        habit.setColor(Color.GREEN);
        habit.setPriority(Priority.HIGH);
        habit.setDescription("Test");
        habit.setTitle("Title");

        Mockito.when(userService.getById(0)).thenReturn(user);

        habitService.addHabit(userCredentials, habit);

        Habit newHabit = new Habit();
        newHabit.setHabitList(null);
        newHabit.setId(0L);
        newHabit.setRepeats(0L);
        newHabit.setColor(Color.GREEN);
        newHabit.setPriority(Priority.HIGH);
        newHabit.setDescription("Test2");
        newHabit.setTitle("Title2");

        Mockito.when(habitRepository.findById(0L)).thenReturn(Optional.of(habit));

        habitService.updateHabit(0L, newHabit);
        var g = habitService.getHabit(0L);

        Assertions.assertEquals(newHabit, g);

        Mockito.verify(habitRepository, Mockito.times(2)).save(habit);
    }
}
