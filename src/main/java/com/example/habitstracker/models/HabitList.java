package com.example.habitstracker.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "habit_list")
@Getter
@Setter
public class HabitList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "habitList")
    private List<Habit> habits;

    public HabitList() {

    }
    
    public HabitList(String name){
        this.name = name;
    }

    public HabitList(Long id, String name, List<Habit> habits) {
        this.id = id;
        this.name = name;
        this.habits = habits;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HabitList habitList)) return false;

        return Objects.equals(id, habitList.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
