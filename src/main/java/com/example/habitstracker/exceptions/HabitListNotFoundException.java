package com.example.habitstracker.exceptions;

public class HabitListNotFoundException extends RuntimeException {
    public HabitListNotFoundException(Long id) {
        super(String.format("HabitList with id id%s has not found", id));
    }

    public HabitListNotFoundException(String name){
        super(String.format("HabitLists with name name%s has not found", name));
    }

}
