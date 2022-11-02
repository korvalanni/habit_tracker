package com.example.habitstracker.utils;

import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.persistence.Table;

/**
 * Инструменты для работы с тестовой базой донных
 */
public class DatabaseUtils {
    /**
     * Очистить таблицы для сущностей {@link User}, {@link Habit}, {@link HabitList}
     */
    public static void clear(JdbcTemplate jdbcTemplate) {
        String userTable = getTableNameByEntity(User.class);
        String habitTable = getTableNameByEntity(Habit.class);
        String habitList = getTableNameByEntity(HabitList.class);
        String habitDoneDates = "habit_done_dates";
        JdbcTestUtils.deleteFromTables(jdbcTemplate, userTable, habitDoneDates, habitTable, habitList);
    }

    private static <T> String getTableNameByEntity(Class<T> item) {
        return item.getAnnotation(Table.class).name();
    }
}
