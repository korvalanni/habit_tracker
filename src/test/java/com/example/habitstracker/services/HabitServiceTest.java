package com.example.habitstracker.services;

import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.User;
import com.example.habitstracker.repository.HabitListRepository;
import com.example.habitstracker.repository.HabitRepository;
import com.example.habitstracker.security.UserCredentials;
import com.example.openapi.dto.Color;
import com.example.openapi.dto.Priority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class HabitServiceTest {
    @InjectMocks
    private HabitService habitService;

    @Mock
    private HabitRepository habitRepository;
    @Mock
    private UserService userService;
    @Mock
    private HabitListRepository habitListRepository;

    @BeforeEach
    public void initMocks() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    /**
     * Добавление привычки в список привычек
     */
    @Test
    void test_addHabit() {
        HabitList habitList = new HabitList();

        UserEntity user = new UserEntity();
        user.setPassword("123");
        user.setHabitList(habitList);
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
    }

    /**
     * Удаление привычки из списка привычек
     */
    @Test
    void test_deleteHabit() {
        UserEntity user = new UserEntity();
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

    /**
     * Обновление привычки в списке привычек
     */
    @Test
    void test_updateHabit() {
        UserEntity user = new UserEntity();
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
        var gotHabit = habitService.getHabit(0L);

        Assertions.assertEquals(newHabit, gotHabit);

        Mockito.verify(habitRepository, Mockito.times(2)).save(habit);
    }
}
