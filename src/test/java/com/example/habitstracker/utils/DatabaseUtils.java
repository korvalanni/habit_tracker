package com.example.habitstracker.utils;

import javax.persistence.Table;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;

/**
 * Инструменты для работы с тестовой базой донных
 */
public class DatabaseUtils {
    /**
     * Очистить таблицы для сущностей {@link UserEntity}, {@link Habit}, {@link HabitList}
     */
    public static void clear(JdbcTemplate jdbcTemplate) {
        String userTable = getTableNameByEntity(UserEntity.class);
        String habitTable = getTableNameByEntity(Habit.class);
        String habitList = getTableNameByEntity(HabitList.class);
        String habitDoneDates = "habit_done_dates";
        JdbcTestUtils.deleteFromTables(jdbcTemplate, userTable, habitDoneDates, habitTable, habitList);
    }

    private static <T> String getTableNameByEntity(Class<T> item) {
        return item.getAnnotation(Table.class).name();
    }
}
