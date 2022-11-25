package com.example.habitstracker.mappers;

import com.example.habitstracker.models.Habit;
import com.example.openapi.dto.HabitDTO;
import org.springframework.stereotype.Component;

/**
 * Конвертирует {@link HabitDTO} в {@link Habit}
 */
@Component
public class HabitDeserializer extends Mapper<HabitDTO, Habit> {
    public HabitDeserializer() {
        super(HabitDTO.class, Habit.class);
    }

    @Override
    public void map(HabitDTO from, Habit to) {
        to.setTitle(from.getTitle());
        to.setDescription(from.getDescription());
        to.setPriority(from.getPriority());
        to.setColor(from.getColor());
        to.setRepeats(from.getRepeats());
        to.setDoneDates(from.getDoneDates());
    }
}
