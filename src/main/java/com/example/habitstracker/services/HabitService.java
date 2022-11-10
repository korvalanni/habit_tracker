package com.example.habitstracker.services;

import com.example.habitstracker.exceptions.HabitNotFoundException;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.User;
import com.example.habitstracker.repository.HabitRepository;
import com.example.habitstracker.security.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component // нужно для контейнерной зависимости DI
public class HabitService {
    private final UserService userService;
    private final HabitRepository habitRepository;

    @Autowired // вместо переменных подставь конкретные объекты
    public HabitService(UserService userService, HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
        this.userService = userService;
    }

    public Habit addHabit(UserCredentials credentials, Habit habit) {
        User user = userService.getById(credentials.id());
        HabitList habitList = user.getHabitList();
        habit.setHabitList(habitList);
        habitRepository.save(habit);
        return habit;
    }

    public Habit getHabit(long id) {
        Optional<Habit> habit = habitRepository.findById(id);
        if (habit.isEmpty())
            throw new HabitNotFoundException(id);
        return habit.get();
    }

    public List<Habit> getHabits() {
        return habitRepository.findAll();
    }

    public void deleteHabit(long id) {
        habitRepository.deleteById(id);
    }

    /**
     * Обновление привычки 
     * @param id -- идишник привычки
     * @param habit -- привычка с полями, которые мы хотим обновить у привычки в базе с указанным id
     */
    public void updateHabit(long id, Habit habit) {
        Optional<Habit> habit1 = habitRepository.findById(id);
        if (habit1.isEmpty())
            throw new HabitNotFoundException(id);
        Habit habit2 = habit1.get();
        
        if (habit.getHabitList() != null)
            habit2.setHabitList(habit.getHabitList());
        if (habit.getTitle() != null)
            habit2.setTitle(habit.getTitle());
        if (habit.getColor() != null)
            habit2.setColor(habit.getColor());
        if (habit.getPriority() != null)
            habit2.setPriority(habit.getPriority());
        if (habit.getRepeats() != null)
            habit2.setRepeats(habit.getRepeats());
        if (habit.getDescription() != null)
            habit2.setDescription(habit.getDescription());
        habitRepository.save(habit2);
    }
}
