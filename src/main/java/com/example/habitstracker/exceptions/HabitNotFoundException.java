package com.example.habitstracker.exceptions;

public class HabitNotFoundException extends RuntimeException {
    public HabitNotFoundException(long id) {
        super(String.format("Habit with id %s has not found", id));
    }
}
