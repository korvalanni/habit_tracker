package com.example.habitstracker.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "habit")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Priority priority;
    private Color color;
    private Long repeats;
    @ManyToOne
    private HabitList habitList;

    public Habit() {

    }

    public Habit(Long id, String name, String description, Priority priority, Color color, Long repeats) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.color = color;
        this.repeats = repeats;
    }

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

    public HabitList getHabitList() {
        return habitList;
    }

    public void setHabitList(HabitList habitList) {
        this.habitList = habitList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Habit habit)) return false;

        return Objects.equals(id, habit.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
