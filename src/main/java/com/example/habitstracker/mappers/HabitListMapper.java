package com.example.habitstracker.mappers;

import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.services.MapperService;
import com.example.openapi.dto.HabitDTO;
import com.example.openapi.dto.HabitListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Сериализатор и десериализатор для {@link HabitList}
 */
public class HabitListMapper {
    /**
     * Конвертирует {@link HabitList} в {@link HabitListDTO}
     */
    @Component
    public static class Serializer extends Mapper<HabitList, HabitListDTO> {
        @Autowired
        private MapperService mapperService;

        public Serializer() {
            super(HabitList.class, HabitListDTO.class);
        }

        @Override
        public void map(HabitList from, HabitListDTO to) {
            Long id = from.getId();
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

            to.setId(id);
            to.setName(name);
            to.setHabits(habits);
        }
    }

    /**
     * Конвертирует {@link HabitListDTO} в {@link HabitList}
     */
    @Component
    public static class Deserializer extends Mapper<HabitListDTO, HabitList> {
        @Autowired
        private MapperService mapperService;

        public Deserializer() {
            super(HabitListDTO.class, HabitList.class);
        }

        @Override
        public void map(HabitListDTO from, HabitList to) {
            Long id = from.getId();
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

            to.setId(id);
            to.setName(name);
            to.setHabits(habits);
        }
    }

}
