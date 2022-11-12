package com.example.habitstracker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.habitstracker.exceptions.HabitListNotFoundException;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.repository.HabitListRepository;
import com.example.openapi.dto.HabitListDTO;

@Component
public class HabitListService {
    private final HabitListRepository habitListRepository;
    private final MapperService mapperService;

    @Autowired
    public HabitListService(HabitListRepository habitListRepository, MapperService mapperService) {
        this.habitListRepository = habitListRepository;
        this.mapperService = mapperService;
    }

    public HabitList addHabitList(HabitListDTO habitListDTO) {
        HabitList habitList = new HabitList();
        mapperService.transform(habitListDTO, habitList);
        habitListRepository.save(habitList);
        return habitList;
    }

    public void addHabitList(HabitList habitList) {
        habitListRepository.save(habitList);
    }

    public HabitList getHabitListWithId(Long id) {
        Optional<HabitList> habitListOpt = habitListRepository.findById(id);
        if (habitListOpt.isEmpty())
            throw new HabitListNotFoundException(id);
        return habitListOpt.get();
    }

    public List<HabitList> getListsWithName(String name) {
        Optional<List<HabitList>> habitListOpt = habitListRepository.findByName(name);
        if (habitListOpt.get().isEmpty())
            throw new HabitListNotFoundException(name);
        return habitListOpt.get();
    }

}
