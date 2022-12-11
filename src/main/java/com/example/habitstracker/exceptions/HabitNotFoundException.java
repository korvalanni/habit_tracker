package com.example.habitstracker.exceptions;

import com.example.habitstracker.constants.ExceptionTextConstants;

public class HabitNotFoundException extends RepresentableException {
    public HabitNotFoundException(long id) {
        super(ExceptionTextConstants.HABIT_NOT_FOUND.formatted(id), ErrorCodes.HABIT_NOT_FOUND);
    }
}
