package com.example.habitstracker.repository;

import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface HabitListRepository extends CrudRepository<HabitList, Long> {
    /**
     * Ищем лист привычек по id
     **/
    Optional<HabitList> findById(Long id);

    Optional<List<HabitList>> findByName(String name);
}
