package com.example.habitstracker.dsl;

import com.example.habitstracker.dto.HabitDTO;
import com.example.habitstracker.mappers.HabitMapper;
import com.example.habitstracker.models.Habit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;

import java.util.List;

import static io.restassured.RestAssured.given;

public class HabitDSL {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void createHabit(Habit habit) throws JsonProcessingException {
        HabitDTO habitDTO = HabitMapper.toDTO(habit);
        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .body(OBJECT_MAPPER.writeValueAsString(habitDTO))
                .header("Authorization", TokenHolder.token)
            .when()
                .post("/habit/create_habit")
            .then()
                .statusCode(200);
        // @formatter:on
    }

    public static void deleteHabit(Habit habit) throws JsonProcessingException {
        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .body(OBJECT_MAPPER.writeValueAsString(habit))
                .header("Authorization", TokenHolder.token)
                .when()
                .delete("/habit/delete_habit")
                .then()
                .statusCode(200);
        // @formatter:on
    }

    public static Habit getHabit(String id) throws JsonProcessingException {
        // @formatter:off
        String json = given()
                .header("Authorization", TokenHolder.token)
                .when()
                .post("/habit/get_habit/" + id)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        // @formatter:on

        return OBJECT_MAPPER.readValue(json, Habit.class);
    }
}
