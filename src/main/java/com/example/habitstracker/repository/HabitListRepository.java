package com.example.habitstracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.habitstracker.models.HabitList;

public interface HabitListRepository extends CrudRepository<HabitList, Long> {
    /**
     * Ищем лист привычек по id
     **/
    Optional<HabitList> findById(Long id);

    /**
     * Найти список привычек по имени
     *
     * @param name имя по которому нужно найти список привычек
     * @return список привычек или пустой Optional
     */
    Optional<List<HabitList>> findByName(String name);
}
