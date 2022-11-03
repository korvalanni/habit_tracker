package com.example.habitstracker.mappers;

import org.springframework.stereotype.Component;

import com.example.habitstracker.models.Habit;
import com.example.openapi.dto.HabitDTO;


@Component
public class HabitMapper {
    public static Habit toEntity(HabitDTO habitDTO) {
        Habit habit = new Habit();

        habit.setTitle(habitDTO.getTitle());
        habit.setDescription(habitDTO.getDescription());
        habit.setPriority(habitDTO.getPriority());
        habit.setColor(habitDTO.getColor());
        habit.setRepeats(habitDTO.getRepeats());
        habit.setDoneDates(habitDTO.getDoneDates());

        return habit;
    }

    public static HabitDTO toDTO(Habit habit) {
        HabitDTO habitDTO = new HabitDTO();

        habitDTO.setTitle(habit.getTitle());
        habitDTO.setDescription(habit.getDescription());
        habitDTO.setPriority(habit.getPriority());
        habitDTO.setColor(habit.getColor());
        habitDTO.setRepeats(habit.getRepeats());
        habitDTO.setDoneDates(habit.getDoneDates());

        return habitDTO;
    }
}
