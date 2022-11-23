package com.example.habitstracker.repository;

import com.example.habitstracker.models.UserPassword;
import org.springframework.data.repository.CrudRepository;

public interface UserPasswordRepository extends CrudRepository<UserPassword, Long> {
}
