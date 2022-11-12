package com.example.habitstracker.mappers;

import com.example.habitstracker.models.Habit;
import com.example.openapi.dto.HabitDTO;
import org.springframework.stereotype.Component;

/**
 * Сериализатор и десериализатор для {@link Habit}
 */
public class HabitMapper {
    /**
     * Конвертирует {@link Habit} в {@link HabitDTO}
     */
    @Component
    public static class Serializer extends Mapper<Habit, HabitDTO> {
        public Serializer() {
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

    /**
     * Конвертирует {@link HabitDTO} в {@link Habit}
     */
    @Component
    public static class Deserializer extends Mapper<HabitDTO, Habit> {
        public Deserializer() {
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

}
