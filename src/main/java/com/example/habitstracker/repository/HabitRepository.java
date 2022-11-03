package com.example.habitstracker.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.habitstracker.models.Habit;

public interface HabitRepository extends CrudRepository<Habit, Long> {
    List<Habit> findAll();
}
