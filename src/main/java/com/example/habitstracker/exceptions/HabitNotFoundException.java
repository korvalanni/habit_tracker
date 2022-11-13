package com.example.habitstracker.exceptions;

public class HabitNotFoundException extends RepresentableException {
    public HabitNotFoundException(long id) {
        super(String.format("Habit with id %s has not found", id), ErrorCodes.HABIT_NOT_FOUND);
    }
}
