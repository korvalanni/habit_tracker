package com.example.habitstracker.integration.utils.dsl;

import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Класс, который предоставляет несколько удобных методов
 */
public class DSLHelper {
    private static String selectedUsername;
    private static Map<String, String> loginUsers = new HashMap<>();

    public static void clean() {
        loginUsers = new HashMap<>();
    }

    /**
     * Сделать запрос с jwt
     */
    public static RequestSpecification authorized() {
        return given().header(HttpHeaders.AUTHORIZATION, "Bearer " + loginUsers.get(selectedUsername));
    }

    /*
     * Возвращает имя залогиненного пользователя
     */
    public static String getSelectedUsername() {
        return selectedUsername;
    }

    /**
     * Установить имя пользователя, от имени которого будем делать запросы
     */
    public static void selectUsername(String username) {
        DSLHelper.selectedUsername = username;
    }

    /**
     * Установить токен авторизации
     */
    public static void setToken(String token) {
        loginUsers.put(selectedUsername, token);
    }

    /**
     * Получить токен выбранного пользователя
     */
    public static String getToken() {
        return loginUsers.get(selectedUsername);
    }
}
