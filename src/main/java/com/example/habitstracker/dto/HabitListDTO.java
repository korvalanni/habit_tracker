package com.example.habitstracker.dto;

import com.example.habitstracker.models.Habit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HabitListDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private List<HabitDTO> habits;

}
