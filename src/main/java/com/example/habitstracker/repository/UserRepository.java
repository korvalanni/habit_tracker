package com.example.habitstracker.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.habitstracker.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * Ищем пользователя по никнейму
     **/
    Optional<User> findByUsername(String username);

}
