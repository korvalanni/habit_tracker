package com.example.habitstracker.dto;

import com.example.habitstracker.models.Habit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class HabitListDTO {
    private Long id;
    private String name;
    private List<Habit> habits;    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HabitListDTO that)) return false;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
