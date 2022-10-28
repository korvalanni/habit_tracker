package com.example.habitstracker.repository;

import com.example.habitstracker.models.HabitList;
import org.springframework.data.repository.CrudRepository;

public interface HabitListRepository extends CrudRepository<HabitList, Long> {
}
