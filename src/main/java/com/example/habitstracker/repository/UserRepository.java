package com.example.habitstracker.repository;

import com.example.habitstracker.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
