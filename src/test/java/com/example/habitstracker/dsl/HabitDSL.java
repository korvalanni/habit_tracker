package com.example.habitstracker.dsl;

import com.example.habitstracker.CleanerService;
import com.example.habitstracker.mappers.HabitMapper;
import com.example.habitstracker.models.Habit;
import com.example.openapi.dto.HabitDTO;
import com.example.openapi.dto.IdDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class HabitDSL {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void createHabit(Habit habit) throws JsonProcessingException {
        HabitDTO habitDTO = HabitMapper.toDTO(habit);

        // @formatter:off
        String result = given()
                .contentType(ContentType.JSON)
                .body(OBJECT_MAPPER.writeValueAsString(habitDTO))
                .header("Authorization", TokenHolder.token)
                .when()
                .post("/habit/create_habit")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        // @formatter:on
        IdDTO id = OBJECT_MAPPER.readValue(result, IdDTO.class);
        habit.setId(id.getId());

        CleanerService.addTask(() -> {
            try {
                HabitDSL.deleteHabit(habit);
            } catch (JsonProcessingException e) {
                Assertions.fail();
            }
        });
    }

    public static void deleteHabit(Habit habit) throws JsonProcessingException {
        var idDto = new IdDTO().id(habit.getId());

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .body(OBJECT_MAPPER.writeValueAsString(idDto))
                .header("Authorization", TokenHolder.token)
                .when()
                .delete("/habit/delete_habit")
                .then()
                .statusCode(200);
        // @formatter:on
    }

    public static HabitDTO getHabit(String id) throws JsonProcessingException {
        // @formatter:off
        String json = given()
                .header("Authorization", TokenHolder.token)
                .when()
                .get("/habit/get_habit/" + id)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        // @formatter:on

        return OBJECT_MAPPER.readValue(json, HabitDTO.class);
    }

    public static void updateHabit(String id, Habit habit) throws JsonProcessingException {

        // @formatter:off
        given()
                .header("Authorization", TokenHolder.token)
                .contentType(ContentType.JSON)
                .body(OBJECT_MAPPER.writeValueAsString(HabitMapper.toDTO(habit)))
                .when()
                .put("/habit/update_habit/{id}", id)
                .then()
                .statusCode(200);
        // @formatter:on
    }
}
