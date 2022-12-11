package com.example.habitstracker.exceptions;

import com.example.habitstracker.constants.ExceptionTextConstants;

public class HabitPermissionException extends RepresentableException{
    public HabitPermissionException(Long id){
        super(ExceptionTextConstants.HABIT_PERMISSION_EXCEPTION.formatted(id), ErrorCodes.HABIT_PERMISSION_EXCEPTION);
    }
}
