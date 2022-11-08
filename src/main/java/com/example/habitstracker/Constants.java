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
     * Пути для swagger
     */
    public static class Swagger {
        public static final String UI_ANY = "/swagger-ui/**";
        public static final String UI_HTML = "/swagger-ui.html";
        public static final String API_DOCS = "/v3/api-docs/**";
    }

    /**
     * Ключи в jwt
     */
    public static class JWTClaims {
        public static final String USER_ID = "id";
    }
}
