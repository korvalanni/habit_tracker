package com.example.habitstracker.unit.tests;

import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.exceptions.UserNotFoundException;
import com.example.habitstracker.mappers.HabitListMapper;
import com.example.habitstracker.mappers.UserMapper;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
import com.example.habitstracker.repository.UserRepository;
import com.example.habitstracker.services.HabitListService;
import com.example.habitstracker.services.MapperService;
import com.example.habitstracker.services.UserService;
import com.example.openapi.dto.HabitListDTO;
import com.example.openapi.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class UserServiceTest extends AbstractUnitTest {
    private UserService userService;
    private UserRepository userRepository;
    private HabitListService habitListService;

    @BeforeEach
    public void initMocks() {
        userRepository = Mockito.mock(UserRepository.class);
        habitListService = Mockito.mock(HabitListService.class);
        mapperService = Mockito.mock(MapperService.class);

        userService = new UserService(userRepository, habitListService, mapperService);

        setupMapperService(UserDTO.class, UserEntity.class, new UserMapper.Deserializer());
    }

    private UserDTO buildSimpleUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("1");
        userDTO.setUsername("a");
        userDTO.setHabitListName("b");

        return userDTO;
    }

    private UserEntity buildSimpleUser() {
        UserEntity user = new UserEntity();
        user.setPassword("1");
        user.setUsername("a");
        user.setHabitList(new HabitList());
        user.setUserId(0L);

        return user;
    }

    /**
     * Добавление пользователя
     */
    @Test
    void test_addUser() {
        UserDTO userDTO = buildSimpleUserDTO();

        Mockito.when(userRepository.findByUsername("a")).thenReturn(Optional.empty());

        userService.addUser(userDTO);
    }

    /**
     * Добавление пользователя с уже занятым никнеймом
     */
    @Test
    void test_addTwiceUser() {
        UserDTO userDTO = buildSimpleUserDTO();

        Mockito.when(userRepository.findByUsername("a")).thenReturn(Optional.of(new UserEntity()));

        Assertions.assertThrowsExactly(UserExistException.class, () -> userService.addUser(userDTO));
    }

    // todo: addUser: пустой ник
    // todo: addUser: пустой пароль
    // todo: addUser: пустое имя списка

    /**
     * Получение пользователя по имени
     */
    @Test
    void test_getByUsername() {
        UserEntity user = buildSimpleUser();

        Mockito.when(userRepository.findByUsername("a")).thenReturn(Optional.of(user));

        UserEntity result = userService.getByUsername("a");

        Assertions.assertEquals(user, result);
    }

    /**
     * Проверили получение пользователя по несуществующему имени
     */
    @Test
    void test_getByNotExistingUsername() {
        UserEntity user = buildSimpleUser();

        Mockito.when(userRepository.findByUsername("a")).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getByUsername("a"));
    }

    // todo: проверить с пустой строкой
    // todo: проверить с null вместо имени

    /**
     * Получение пользователя по id
     */
    @Test
    void test_getById() {
        UserEntity user = buildSimpleUser();

        Mockito.when(userRepository.findById(0L)).thenReturn(Optional.of(user));

        UserEntity result = userService.getById(0L);

        Assertions.assertEquals(user, result);
    }

    /**
     * Получение пользователя по несуществующему id
     */
    @Test
    void test_getByNotExistingId() {
        UserEntity user = buildSimpleUser();

        Mockito.when(userRepository.findById(0L)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getById(0L));
    }

    /**
     * Удаление пользователя
     */
    @Test
    void test_deleteByUsername() {
        UserEntity user = buildSimpleUser();

        Mockito.when(userRepository.findByUsername("a")).thenReturn(Optional.of(user));
        userService.deleteByUsername("a");

        Mockito.when(userRepository.findByUsername("a")).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.deleteByUsername("a"));
    }

    /**
     * Обновление пароля пользователя
     */
    @Test
    void test_updateUserByUsername() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("123");
        userDTO.setUsername("a");

        UserEntity user = buildSimpleUser();

        Mockito.when(userRepository.findByUsername("a")).thenReturn(Optional.of(user));

        userService.updateUserByUsername("a", userDTO);

        UserEntity result = userService.getByUsername("a");

        Assertions.assertEquals("123", result.getPassword());
    }

    /**
     * Проверили обновление несуществующего пользователя
     */
    @Test
    void test_updateUserPasswordByNotExistingUsername() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("123");
        userDTO.setUsername("a");
        userDTO.setHabitListName("b");

        UserEntity user = buildSimpleUser();

        Mockito.when(userRepository.findByUsername("a")).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.updateUserByUsername("a", userDTO));

        Assertions.assertEquals("1", user.getPassword());
    }

    /**
     * Получение списка привычек
     */
    @Test
    void test_getUserHabitList() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("123");
        userDTO.setUsername("a");
        userDTO.setHabitListName("b");

        UserEntity user = buildSimpleUser();

        Mockito.when(userRepository.findByUsername("a")).thenReturn(Optional.of(user));

        HabitList result = userService.getUserHabitList(userDTO);

        Assertions.assertEquals(new HabitList(), result);
    }

    /**
     * Получение списка привычек у несуществующешго пользователя
     */
    @Test
    void test_getUserHabitListByNotExistingName() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("123");
        userDTO.setUsername("a");
        userDTO.setHabitListName("b");

        UserEntity user = buildSimpleUser();

        Mockito.when(userRepository.findByUsername("a")).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserHabitList(userDTO));
    }
}