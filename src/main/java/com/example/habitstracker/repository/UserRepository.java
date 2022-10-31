package com.example.habitstracker.repository;

import com.example.habitstracker.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * Ищем пользователя по никнейму
     **/
    Optional<User> findByUsername(String username);

}
