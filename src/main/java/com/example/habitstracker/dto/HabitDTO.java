package com.example.habitstracker.dto;

import com.example.habitstracker.models.Color;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.Priority;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class HabitDTO {

    private Long id;
    private HabitList habitList;
    private String name;
    private String description;
    private Priority priority;
    private Color color;
    private Long repeats;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HabitDTO habitDTO)) return false;

        return Objects.equals(id, habitDTO.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
