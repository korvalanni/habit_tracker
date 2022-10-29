package com.example.habitstracker.dto;

import com.example.habitstracker.models.Color;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.Priority;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HabitDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private HabitList habitList;
    private String name;
    private String description;
    private Priority priority;
    private Color color;
    private Long repeats;

}
