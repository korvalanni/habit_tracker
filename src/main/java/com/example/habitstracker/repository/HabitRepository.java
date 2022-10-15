package com.example.habitstracker.repository;

import com.example.habitstracker.models.Habit;
import org.springframework.data.repository.CrudRepository;

public interface HabitRepository extends CrudRepository<Habit, Long> {

}
