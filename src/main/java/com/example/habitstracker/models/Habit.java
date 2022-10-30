package com.example.habitstracker.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "habit")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String description;
    private Priority priority;
    private Color color;
    private Long repeats;
    @ManyToOne
    private HabitList habitList;


    public Habit(Long id, String name, String description, Priority priority, Color color, Long repeats) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.color = color;
        this.repeats = repeats;
    }

}
