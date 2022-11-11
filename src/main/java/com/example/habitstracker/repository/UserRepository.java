package com.example.habitstracker.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.habitstracker.models.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    /**
     * Ищем пользователя по никнейму
     **/
    Optional<UserEntity> findByUsername(String username);

}
