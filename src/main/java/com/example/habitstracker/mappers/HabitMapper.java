package com.example.habitstracker.mappers;

import com.example.habitstracker.dto.HabitDTO;
import com.example.habitstracker.models.Color;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.Priority;
import org.springframework.stereotype.Component;


@Component
public class HabitMapper {
    public static Habit toEntity(HabitDTO habitDTO) {
        Long id = habitDTO.getId();
        String name = habitDTO.getName();
        String description = habitDTO.getDescription();
        Priority priority = habitDTO.getPriority();
        Color color = habitDTO.getColor();
        Long repeats = habitDTO.getRepeats();
        HabitList habitList = habitDTO.getHabitList(); //toDO нарушаю принцип разделения ответственности SRP как не нарушать?

        Habit habit = new Habit();

        habit.setId(id);
        habit.setName(name);
        habit.setDescription(description);
        habit.setPriority(priority);
        habit.setColor(color);
        habit.setRepeats(repeats);
        habit.setHabitList(habitList);

        return habit;
    }

    public static HabitDTO toDTO(Habit habit) {
        Long id = habit.getId();
        String name = habit.getName();
        String description = habit.getDescription();
        Priority priority = habit.getPriority();
        Color color = habit.getColor();
        Long repeats = habit.getRepeats();
        HabitList habitList = habit.getHabitList();
        HabitDTO habitDTO = new HabitDTO();

        habitDTO.setId(id);
        habitDTO.setName(name);
        habitDTO.setDescription(description);
        habitDTO.setPriority(priority);
        habitDTO.setColor(color);
        habitDTO.setRepeats(repeats);
        habitDTO.setHabitList(habitList);

        return habitDTO;
    }
}
