package com.example.habitstracker.services;

import com.example.habitstracker.exceptions.HabitNotFoundException;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
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
        UserEntity user = userService.getById(credentials.id());
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
     * @param updatedHabit -- привычка с полями, которые мы хотим обновить у привычки в базе с указанным id
     */
    public void updateHabit(long id, Habit updatedHabit) {
        Optional<Habit> oldHabitOpt = habitRepository.findById(id);
        if (oldHabitOpt.isEmpty())
            throw new HabitNotFoundException(id);
        Habit dataBaseHabit = oldHabitOpt.get();

        if (updatedHabit.getHabitList() != null)
            dataBaseHabit.setHabitList(updatedHabit.getHabitList());
        if (updatedHabit.getTitle() != null)
            dataBaseHabit.setTitle(updatedHabit.getTitle());
        if (updatedHabit.getColor() != null)
            dataBaseHabit.setColor(updatedHabit.getColor());
        if (updatedHabit.getPriority() != null)
            dataBaseHabit.setPriority(updatedHabit.getPriority());
        if (updatedHabit.getRepeats() != null)
            dataBaseHabit.setRepeats(updatedHabit.getRepeats());
        if (updatedHabit.getDescription() != null)
            dataBaseHabit.setDescription(updatedHabit.getDescription());
        habitRepository.save(dataBaseHabit);
    }
}
