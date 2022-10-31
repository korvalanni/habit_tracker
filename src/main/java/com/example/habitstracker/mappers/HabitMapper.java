package com.example.habitstracker.mappers;

import com.example.habitstracker.dto.HabitDTO;
import com.example.habitstracker.models.Color;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.Priority;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class HabitMapper {
    public static Habit toEntity(HabitDTO habitDTO) {
        Habit habit = new Habit();

        habit.setTitle(habitDTO.getTitle());
        habit.setDescription(habitDTO.getDescription());
        habit.setPriority(habitDTO.getPriority());
        habit.setColor(habitDTO.getColor());
        habit.setRepeats(habitDTO.getRepeats());
        habit.setDoneDates(habitDTO.getDone_dates());

        return habit;
    }

    public static HabitDTO toDTO(Habit habit) {
        HabitDTO habitDTO = new HabitDTO();

        habitDTO.setTitle(habit.getTitle());
        habitDTO.setDescription(habit.getDescription());
        habitDTO.setPriority(habit.getPriority());
        habitDTO.setColor(habit.getColor());
        habitDTO.setRepeats(habit.getRepeats());
        habitDTO.setDone_dates(habit.getDoneDates());

        return habitDTO;
    }
}
