package com.example.habitstracker;

/**
 * Все константы проекта
 */
public class Constants {
    /**
     * Пути в api
     */
    public static class API {
        public static final String LOGIN = "/auth/login";
        public static final String REGISTRATION = "/auth/registration";
    }

    /**
     * Ключи в jwt
     */
    public static class JWTClaims {
        public static final String USER_ID = "id";
    }
}
