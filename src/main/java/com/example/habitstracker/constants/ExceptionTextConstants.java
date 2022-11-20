package com.example.habitstracker.constants;

import org.springframework.http.HttpHeaders;

public class ExceptionTextConstants {
    public static final String EMPTY_AUTHORIZATION_HEADER =
            "You must fill value of the " + HttpHeaders.AUTHORIZATION + " header";
    public static final String INCORRECT_CLAIMS = "Incorrect claims in jwt";
    public static final String INCORRECT_CREDENTIALS = "Incorrect login or password";
    public static final String INCORRECT_JWT = "Incorrect JWT";
    public static final String SKIPPED_AUTHORIZATION_HEADER = "You forgot to set Authorization header";
    public static final String HABIT_LIST_NOT_FOUND_ID = "HabitList with id id %s has not found";
    public static final String HABIT_LIST_NOT_FOUND_USERNAME = "HabitLists with name name %s has not found";
    public static final String HABIT_NOT_FOUND = "Habit with id %s has not found";
    public static final String MAPPER_NOT_FOUND = "Mapper not found! Can't map from type %s to type %s";
    public static final String USER_ALREADY_EXISTS = "User with username %s has already registered";
    public static final String USER_NOT_FOUND_ID = "User with id %s has not found";
    public static final String USER_NOT_FOUND_USERNAME = "User with username %s has not found";
}
