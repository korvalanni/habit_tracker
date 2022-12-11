package com.example.habitstracker.exceptions;

import com.example.habitstracker.constants.ExceptionTextConstants;

public class HabitListNotFoundException extends RuntimeException {
    public HabitListNotFoundException(Long id) {
        super(ExceptionTextConstants.HABIT_LIST_NOT_FOUND_ID.formatted(id));
    }

    public HabitListNotFoundException(String name){
        super(ExceptionTextConstants.HABIT_LIST_NOT_FOUND_USERNAME.formatted(name));
    }
}
