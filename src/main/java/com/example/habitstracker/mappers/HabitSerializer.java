package com.example.habitstracker.mappers;

import com.example.habitstracker.models.Habit;
import com.example.openapi.dto.HabitDTO;
import org.springframework.stereotype.Component;

/**
 * Конвертирует {@link Habit} в {@link HabitDTO}
 */
@Component
public class HabitSerializer extends Mapper<Habit, HabitDTO> {
    public HabitSerializer() {
        super(Habit.class, HabitDTO.class);
    }

    @Override
    public void map(Habit from, HabitDTO to) {
        to.setTitle(from.getTitle());
        to.setDescription(from.getDescription());
        to.setPriority(from.getPriority());
        to.setColor(from.getColor());
        to.setRepeats(from.getRepeats());
        to.setDoneDates(from.getDoneDates());
    }
}
