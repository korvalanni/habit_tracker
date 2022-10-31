package com.example.habitstracker.dto;

import com.example.habitstracker.models.Color;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.Priority;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HabitDTO {
    private String title;
    private String description;
    private Priority priority;
    private Color color;
    private Long repeats;
    private List<Long> done_dates;
}
