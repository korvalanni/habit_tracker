package com.example.habitstracker.controllers;

import com.example.habitstracker.models.Habit;

import java.util.List;

public class HabitListDTO {
    private Long id;
    private String name;
    private List<Habit> habits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Habit> getHabits() {
        return habits;
    }

    public void setHabits(List<Habit> habits) {
        this.habits = habits;
    }
    //toDO equals hashcode
}
