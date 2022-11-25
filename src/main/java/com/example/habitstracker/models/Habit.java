package com.example.habitstracker.models;

import com.example.habitstracker.constants.TableNameConstants;
import com.example.openapi.dto.Color;
import com.example.openapi.dto.Priority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = TableNameConstants.HABIT)
@Getter
@Setter
@NoArgsConstructor
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Habit habit = (Habit) o;
        return id != null && Objects.equals(id, habit.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
