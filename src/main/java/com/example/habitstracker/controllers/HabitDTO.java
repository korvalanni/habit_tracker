package com.example.habitstracker.controllers;

import com.example.habitstracker.models.Color;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.Priority;

public class HabitDTO {

    private Long id;
    private HabitList habitList; // foreign key какая аннотация???
    private String name;
    private String description;
    private Priority priority;
    private Color color; // foreign key
    private Long repeats;

    public Long getId() {
        return id;
    }

    public HabitList getHabitList() {
        return habitList;
    }

    public void setHabitList(HabitList habitList) {
        this.habitList = habitList;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Long getRepeats() {
        return repeats;
    }

    public void setRepeats(Long repeats) {
        this.repeats = repeats;
    }

    //toDO equals hashcode
}
