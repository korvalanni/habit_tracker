package com.example.habitstracker.mappers;

import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.services.MapperService;
import com.example.openapi.dto.HabitListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Конвертирует {@link HabitListDTO} в {@link HabitList}
 */
@Component
public class HabitListDeserializer extends Mapper<HabitListDTO, HabitList> {
    @Autowired
    private MapperService mapperService;

    public HabitListDeserializer() {
        super(HabitListDTO.class, HabitList.class);
    }

    @Override
    public void map(HabitListDTO from, HabitList to) {
        String name = from.getName();

        List<Habit> habits = from
                .getHabits()
                .stream()
                .map(item -> {
                    Habit habit = new Habit();
                    mapperService.transform(item, habit);
                    return habit;
                })
                .toList();

        to.setName(name);
        to.setHabits(habits);
    }
}