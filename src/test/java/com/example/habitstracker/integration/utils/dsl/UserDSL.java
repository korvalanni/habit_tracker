package com.example.habitstracker.integration.utils.dsl;

import com.example.habitstracker.constants.ApiConstants;
import com.example.habitstracker.models.UserEntity;
import com.example.openapi.dto.ChangePasswordDTO;
import com.example.openapi.dto.UserWithoutPasswordDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.habitstracker.integration.utils.dsl.DSLHelper.authorized;
import static io.restassured.RestAssured.given;

/**
 * Инструменты для взаимодействия с api пользователя
 */
@Component
public class UserDSL {
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Удалить пользователя
     */
    public void deleteUser() {
        // @formatter:off
        authorized()
            .when()
                .delete(ApiConstants.User.DELETE_USER)
            .then()
                .statusCode(200);
        // @formatter:on
    }

    /**
     * Получить пользователя
     *
     * @param username Ник пользователя, которого ъотим получить
     */
    public void getUser(String username) {
        // @formatter:off
        authorized()
            .when()
                .post(ApiConstants.User.GET_USER, username)
            .then()
                .statusCode(200);
        // @formatter:on
    }

    // todo: надо разобраться с api

    /**
     * Обновить пользователя
     *
     * @param username Ник пользователя
     * @param user     Новые данные
     */
    public void updateUser(UserEntity user) throws JsonProcessingException {
        UserWithoutPasswordDTO userDTO = new UserWithoutPasswordDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setHabitListName(user.getHabitList().getName());

        // @formatter:off
       authorized()
               .contentType(ContentType.JSON)
               .body(objectMapper.writeValueAsString(userDTO))
           .when()
               .put(ApiConstants.User.UPDATE_USER)
           .then()
               .statusCode(200);
        // @formatter:on
    }

    /**
     * Обновить пароль пользователя
     *
     * @param username Ник пользователя
     * @param user     Новые данные
     */
    public void updatePassword(String oldPassword, String newPassword) throws JsonProcessingException {
        ChangePasswordDTO dto = new ChangePasswordDTO();
        dto.setOldPassword(oldPassword);
        dto.setNewPassword(newPassword);

        // @formatter:off
        authorized()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(dto))
            .when()
                .put(ApiConstants.User.UPDATE_PASSWORD)
            .then()
                .statusCode(200);
        // @formatter:on
    }
}
