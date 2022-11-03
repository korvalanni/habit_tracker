package com.example.habitstracker.services;

import com.example.habitstracker.exceptions.HabitListNotFoundException;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.repository.HabitListRepository;
import com.example.openapi.dto.HabitListDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HabitListServiceTest {
    @InjectMocks
    private HabitListService habitListService;

    @Mock
    private HabitListRepository habitListRepository;

    @BeforeEach
    public void initMocks() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    /**
     * Добавление списка привычек
     */
    @Test
    void test_addHabitList() {
        HabitListDTO habitListDTO = new HabitListDTO();
        habitListDTO.setId(0L);
        habitListDTO.setName("Test");
        habitListDTO.setHabits(new ArrayList<>());

        habitListService.addHabitList(habitListDTO);
    }

    // todo: проверка доавбления с пустым id
    // todo: проверка доавбления с пустым name
    // todo: проверка доавбления с пустым habits

    /**
     * Получение списка привычек по id
     */
    @Test
    void test_getHabitListWithId() {
        HabitList habitList = new HabitList();
        habitList.setId(0L);
        habitList.setName("Test");
        habitList.setHabits(new ArrayList<>());

        Mockito.when(habitListRepository.findById(0L)).thenReturn(Optional.of(habitList));

        HabitList getHabitList = habitListService.getHabitListWithId(0L);

        Assertions.assertEquals(habitList, getHabitList);
    }

    /**
     * Получение списка привычек по несуществующему id
     */
    @Test
    void test_getHabitListWithNotExistingId() {
        Mockito.when(habitListRepository.findById(0L)).thenReturn(Optional.empty());

        Assertions.assertThrows(HabitListNotFoundException.class, () -> habitListService.getHabitListWithId(0L));
    }

    /**
     * Получение списка привычек по имени
     */
    @Test
    void test_getHabitListWithName() {
        HabitList habitList = new HabitList();
        habitList.setId(0L);
        habitList.setName("Test");
        habitList.setHabits(new ArrayList<>());

        Mockito.when(habitListRepository.findByName("Test")).thenReturn(Optional.of(List.of(habitList)));

        List<HabitList> getHabitList = habitListService.getListsWithName("Test");

        Assertions.assertTrue(getHabitList.contains(habitList));
    }

    /**
     * Получение списка привычек по несуществующему имени
     */
    @Test
    void test_getHabitListWithNotExistingName() {
        Mockito.when(habitListRepository.findByName("Test")).thenReturn(Optional.of(List.of()));

        Assertions.assertThrows(HabitListNotFoundException.class, () -> habitListService.getListsWithName("Test"));
    }
}
