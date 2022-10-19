package com.example.habitstracker.services;

import com.example.habitstracker.controllers.HabitListDTO;
import com.example.habitstracker.mappers.HabitListMapper;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.repository.HabitListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HabitListService {
    private final HabitListRepository habitListRepository;

    @Autowired
    public HabitListService(HabitListRepository habitListRepository) {
        this.habitListRepository = habitListRepository;
    }

    public HabitList addHabitList(HabitListDTO habitListDTO) {
        HabitList habitList = HabitListMapper.toEntity(habitListDTO);
        habitListRepository.save(habitList);
        return habitList;
    }
}
