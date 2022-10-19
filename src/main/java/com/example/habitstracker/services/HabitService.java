package com.example.habitstracker.services;

import com.example.habitstracker.controllers.HabitDTO;
import com.example.habitstracker.mappers.HabitMapper;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // нужно для контейнерной зависимости DI
public class HabitService {

    private final HabitRepository habitRepository;

    @Autowired // вместо переменных подставь конкретные объекты
    public HabitService(HabitRepository habitRepository) {

        this.habitRepository = habitRepository;
    }

    public Habit addHabit(HabitDTO habitDTO) {
        Habit habit = HabitMapper.toEntity(habitDTO);
        habitRepository.save(habit);
        return habit;
    }
}
