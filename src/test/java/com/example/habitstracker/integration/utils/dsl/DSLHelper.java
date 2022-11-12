package com.example.habitstracker.integration.utils.dsl;

import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpHeaders;

import static io.restassured.RestAssured.given;

/**
 * Класс, который предоставляет несколько удобных методов
 */
public class DSLHelper {
    private static String token = null;

    /**
     * Сделать запрос с jwt
     */
    public static RequestSpecification authorized() {
        return given().header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    /**
     * Установить токен авторизации
     */
    public static void setToken(String token) {
        DSLHelper.token = token;
    }

    /**
     * Получить токен авторизации
     */
    public static String getToken() {
        return token;
    }
}
