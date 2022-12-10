package com.example.habitstracker.integration.tests;

import com.example.habitstracker.constants.ApiConstants;
import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.exceptions.HabitNotFoundException;
import com.example.habitstracker.integration.utils.TestHabitBuilder;
import com.example.habitstracker.integration.utils.TestUserBuilder;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
import com.example.habitstracker.repository.HabitListRepository;
import com.example.habitstracker.repository.HabitRepository;
import com.example.habitstracker.repository.UserRepository;
import com.example.openapi.dto.ErrorResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static com.example.habitstracker.integration.utils.dsl.DSLHelper.authorized;

public class DBTest extends AbstractIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HabitListRepository habitListRepository;
    @Autowired
    private HabitRepository habitRepository;

    /**
     * Проверка каскадного удаления
     * 1) Генерируем пользователя
     * 2) Сохраняем HabitList и самого пользователя
     * 3) Создаем и сохраняем две привычки
     * 4) Удаляем пользователя
     * 5) Убеждаемся, что пользователь, список и привычки удалились
     */
    @Test
    void test_cascadeDeleting() {
        UserEntity user = new TestUserBuilder().build();

        habitListRepository.save(user.getHabitList());
        user = userRepository.save(user);

        List<Habit> habits = List.of(
                new TestHabitBuilder().setHabitList(user.getHabitList()).build(),
                new TestHabitBuilder().setHabitList(user.getHabitList()).build());
        user.getHabitList().setHabits(habits);
        habits.forEach(item -> habitRepository.save(item));

        userRepository.deleteById(user.getUserId());

        Optional<UserEntity> userEntity = userRepository.findById(user.getUserId());
        Assertions.assertTrue(userEntity.isEmpty());

        Optional<HabitList> habitList = habitListRepository.findById(user.getHabitList().getId());
        Assertions.assertTrue(habitList.isEmpty());

        habits.forEach(habit -> {
            Optional<Habit> habit1 = habitRepository.findById(habit.getId());
            Assertions.assertTrue(habit1.isEmpty());
        });
    }

    /**
     * Проверка ограничения на уникальность имени
     */
    @Test
    void test_uniqueUserName() {
        UserEntity user = new TestUserBuilder().build();

        userRepository.save(user);
        try {
            userRepository.save(user);
            Assertions.fail();
        } catch (DataIntegrityViolationException exception) {
            Assertions.assertTrue(true);
        } catch (Exception exception) {
            Assertions.fail();
        } finally {
            userRepository.delete(user);
        }
    }    
}
