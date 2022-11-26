package com.example.habitstracker.models;

import com.example.habitstracker.constants.TableNameConstants;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = TableNameConstants.HABIT_LIST)
@Getter
@Setter
@NoArgsConstructor
public class HabitList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "habitList", cascade = CascadeType.ALL)
    private List<Habit> habits;

    public HabitList(String name) {
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
        if (o == null || getClass() != o.getClass()) return false;
        HabitList habitList = (HabitList) o;
        return Objects.equals(habits, habitList.habits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(habits);
    }
}
