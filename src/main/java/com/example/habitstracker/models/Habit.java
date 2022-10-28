package com.example.habitstracker.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "habit")
@Getter
@Setter
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
