package com.example.habitstracker.repository;

import com.example.habitstracker.models.Habit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HabitRepository extends CrudRepository<Habit, Long> {
    List<Habit> findAll();
}
