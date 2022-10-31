package com.example.habitstracker.dsl;

import com.example.habitstracker.Constants;
import com.example.habitstracker.mappers.UserMapper;
import com.example.habitstracker.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static io.restassured.RestAssured.given;

/**
 * Инструменты для взаимодействия с механизмом авторизации
 */
public class AuthDSL {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Зарегистрировать нового пользователя
     *
     * @param user Пользователь
     */
    public static void register(User user) throws JsonProcessingException {
        var dto = UserMapper.toDTO(user);

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .body(OBJECT_MAPPER.writeValueAsString(dto))
            .when()
                .post("/auth/registration")
            .then()
                .statusCode(200);
        // @formatter:on
    }

    /**
     * Залогинится в системе
     *
     * @param user Пользователь, под котором логинимся
     */
    public static void login(User user) throws JsonProcessingException {
        var values = new HashMap<String, String>();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        var json = OBJECT_MAPPER.writeValueAsString(values);

        // @formatter:off
        var response = given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(Constants.API.LOGIN)
                .then()
                .statusCode(200);
        // @formatter:on
        TokenHolder.token = response.extract().header("Authorization").split(" ")[1];
    }

    /**
     * Обертка для выполнения действий.
     * <p>
     * Как работает:
     * 1) логинимся в системе
     * 2) выполняем действия
     * 3) удаляем пользователя
     *
     * @param user     Пользователь
     * @param function Действия
     */
    public static void session(User user, Consumer<User> function) throws JsonProcessingException {
        login(user);
        function.accept(user);
        UserDSL.deleteUser(user);
    }
}
