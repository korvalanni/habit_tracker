package com.example.habitstracker.constants;

/**
 * Пути в api
 */
public class ApiConstants {
    public static class Auth {
        private static final String base = "/auth";
        public static final String LOGIN = base + "/login";
        public static final String REGISTRATION = base + "/registration";
    }

    public static class Habit {
        private static final String base = "/habit";
        public static final String CREATE_HABIT = base + "/create_habit";
        public static final String GET_HABIT = base + "/get_habit/{id}";
        public static final String DELETE_HABIT = base + "/delete_habit";
        public static final String UPDATE_HABIT = base + "/update_habit/{id}";
    }

    public static class HabitList {
        private static final String base = "/habit_list";
        public static final String GET_HABIT_LIST = base + "/get_habitList";
        public static final String UPDATE_HABIT_LIST = base + "/update/{id}";
    }

    public static class User {
        private static final String base = "/user";
        public static final String GET_USER = base + "/get_user/{username}";
        public static final String DELETE_USER = base + "/delete_user/{username}";
        public static final String UPDATE_USER = base + "/update_user";
    }
}
