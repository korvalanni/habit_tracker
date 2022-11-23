package com.example.habitstracker.constants;

/**
 * Пути в api
 */
public class ApiConstants {
    /**
     * Пути в api для авторизации
     */
    public static class Auth {
        private static final String base = "/auth";
        public static final String LOGIN = base + "/login";
        public static final String REGISTRATION = base + "/registration";
    }

    /**
     * Пути в api для взаимодействия с привычками
     */
    public static class Habit {
        private static final String base = "/habit";
        public static final String CREATE_HABIT = base + "/create_habit";
        public static final String GET_HABIT = base + "/get_habit/{id}";
        public static final String DELETE_HABIT = base + "/delete_habit/{id}";
        public static final String UPDATE_HABIT = base + "/update_habit/{id}";
    }

    /**
     * Пути в api для взаимодействия со списками привычек
     */
    public static class HabitList {
        private static final String base = "/habit_list";
        public static final String GET_HABIT_LIST = base + "/get_habitList";
        public static final String UPDATE_HABIT_LIST = base + "/update_habitList";
    }

    /**
     * Пути в api для взаимодействия с аккаунтом пользователя
     */
    public static class User {
        private static final String base = "/user";
        public static final String GET_USER = base + "/get_user/{username}";
        public static final String DELETE_USER = base + "/delete_user";
        public static final String UPDATE_USER = base + "/update_user";
    }
}
