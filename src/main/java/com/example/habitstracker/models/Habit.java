package com.example.habitstracker.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import com.example.openapi.dto.Color;
import com.example.openapi.dto.Priority;

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
    private String title;
    private String description;
    private Priority priority;
    private Color color;
    private Long repeats;
    @ManyToOne
    private HabitList habitList;
    @ElementCollection
    private List<Long> doneDates;

    public Habit(String title, String description, Priority priority, Color color, Long repeats, List<Long> doneDates) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.color = color;
        this.repeats = repeats;
        this.doneDates = doneDates;
    }

}
