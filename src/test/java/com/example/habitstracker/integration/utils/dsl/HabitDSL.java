package com.example.habitstracker.integration.utils.dsl;

import com.example.habitstracker.constants.ApiConstants;
import com.example.habitstracker.integration.utils.CleanerService;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.services.MapperService;
import com.example.openapi.dto.HabitDTO;
import com.example.openapi.dto.IdDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.habitstracker.integration.utils.dsl.DSLHelper.authorized;

/**
 * Инструменты для взаимодействия с api привычек
 */
@Component
public class HabitDSL {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MapperService mapperService;

    public void createHabit(Habit habit) throws JsonProcessingException {
        var habitDTO = new HabitDTO();
        mapperService.transform(habit, habitDTO);

        // @formatter:off
        String result = authorized()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(habitDTO))
            .when()
                .post(ApiConstants.Habit.CREATE_HABIT)
            .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        // @formatter:on
        IdDTO id = objectMapper.readValue(result, IdDTO.class);
        habit.setId(id.getId());

        CleanerService.addTask(() -> {
            try {
                deleteHabit(habit);
            } catch (JsonProcessingException e) {
                Assertions.fail();
            }
        });
    }

    public void deleteHabit(Habit habit) throws JsonProcessingException {
        // @formatter:off
        authorized()
            .when()
                .delete(ApiConstants.Habit.DELETE_HABIT, habit.getId())
            .then()
                .statusCode(200);
        // @formatter:on
    }

    public HabitDTO getHabit(String id) throws JsonProcessingException {
        // @formatter:off
        String json = authorized()
            .when()
                .get(ApiConstants.Habit.GET_HABIT, id)
            .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        // @formatter:on

        return objectMapper.readValue(json, HabitDTO.class);
    }

    public void updateHabit(String id, Habit habit) throws JsonProcessingException {
        var habitDTO = new HabitDTO();
        mapperService.transform(habit, habitDTO);

        // @formatter:off
        authorized()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(habitDTO))
            .when()
                .put(ApiConstants.Habit.UPDATE_HABIT, id)
            .then()
                .statusCode(200);
        // @formatter:on
    }
}
