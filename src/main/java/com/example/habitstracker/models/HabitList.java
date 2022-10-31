package com.example.habitstracker.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "habit_list")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class HabitList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    @OneToMany(mappedBy = "habitList")
    private List<Habit> habits;

    public HabitList(String name){
        this.name = name;
    }


    public HabitList(Long id, String name, List<Habit> habits) {
        this.id = id;
        this.name = name;
        this.habits = habits;
    }

}
