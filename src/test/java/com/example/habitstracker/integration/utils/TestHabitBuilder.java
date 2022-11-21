package com.example.habitstracker.integration.utils;

import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.HabitList;
import com.example.openapi.dto.Color;
import com.example.openapi.dto.Priority;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Класс для создания привычек
 */
public class TestHabitBuilder {
    private long id = 0L;
    private String title = generateUniqueString();
    private String description = generateUniqueString();
    private Priority priority = generatePriority();
    private Color color = generateColor();
    private Long repeats = 1L;
    private HabitList habitList = new HabitList(generateUniqueString());
    private List<Long> doneDates = List.of(3L);

    private String generateUniqueString() {
        return UUID
                .randomUUID()
                .toString()
                .substring(0, 18) // Длина имени должна быть не более 20 символов
                .replace("-", "_"); // Нельзя использовать в имени дефисы
    }

    private Priority generatePriority() {
        Random random = new Random();
        return Priority.values()[random.nextInt(Priority.values().length)];
    }

    private Color generateColor() {
        Random random = new Random();
        return Color.values()[random.nextInt(Color.values().length)];
    }

    public TestHabitBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public TestHabitBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public TestHabitBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public TestHabitBuilder setPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public TestHabitBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public TestHabitBuilder setRepeats(Long repeats) {
        this.repeats = repeats;
        return this;
    }

    public TestHabitBuilder setHabitList(HabitList habitList) {
        this.habitList = habitList;
        return this;
    }

    public TestHabitBuilder setDoneDates(List<Long> doneDates) {
        this.doneDates = doneDates;
        return this;
    }

    public Habit build() {
        Habit habit = new Habit(title, description, priority, color, repeats, doneDates);
        habit.setId(id);
        habit.setHabitList(habitList);
        return habit;
    }
}
