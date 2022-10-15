package com.example.habitstracker.mappers;

import com.example.habitstracker.controllers.HabitListDTO;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.HabitList;

import javax.persistence.OneToMany;
import java.util.List;

public class HabitListMapper {
    public static HabitList toEntity(HabitListDTO habitListDTO) {
        Long id = habitListDTO.getId();
        String name = habitListDTO.getName();
        List<Habit> habits = habitListDTO.getHabits();

        HabitList habitList = new HabitList();
        habitList.setId(id);
        habitList.setName(name);
        habitList.setHabits(habits);

        return habitList;
    }

    public static HabitListDTO listDTO(HabitList habitList) {
        Long id = habitList.getId();
        String name = habitList.getName();
        List<Habit> habits = habitList.getHabits();

        HabitListDTO habitListDTO = new HabitListDTO();
        habitListDTO.setId(id);
        habitListDTO.setName(name);
        habitListDTO.setHabits(habits);

        return habitListDTO;
    }
}
