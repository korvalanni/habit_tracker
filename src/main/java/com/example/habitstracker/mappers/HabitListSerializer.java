package com.example.habitstracker.mappers;

import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.services.MapperService;
import com.example.openapi.dto.HabitDTO;
import com.example.openapi.dto.HabitListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Конвертирует {@link HabitList} в {@link HabitListDTO}
 */
@Component
public class HabitListSerializer extends Mapper<HabitList, HabitListDTO> {
    @Autowired
    private MapperService mapperService;

    public HabitListSerializer() {
        super(HabitList.class, HabitListDTO.class);
    }

    @Override
    public void map(HabitList from, HabitListDTO to) {
        String name = from.getName();

        List<HabitDTO> habits = from
                .getHabits()
                .stream()
                .map(item -> {
                    HabitDTO habit = new HabitDTO();
                    mapperService.transform(item, habit);
                    return habit;
                })
                .toList();

        to.setName(name);
        to.setHabits(habits);
    }
}
