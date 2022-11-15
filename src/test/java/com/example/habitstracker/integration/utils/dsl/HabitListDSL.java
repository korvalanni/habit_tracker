package com.example.habitstracker.integration.utils.dsl;

import com.example.habitstracker.constants.ApiConstants;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.services.MapperService;
import com.example.openapi.dto.HabitListDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.habitstracker.integration.utils.dsl.DSLHelper.authorized;

/**
 * Инструменты для взаимодействия с api листа привычек
 */
@Component
public class HabitListDSL {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MapperService mapperService;

    public void updateHabitList(Long id, HabitList habitList) throws JsonProcessingException{
        var habitListDTO = new HabitListDTO();
        mapperService.transform(habitList, habitListDTO);

        //@formatter:off
       authorized()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(habitListDTO))
                .when()
                .put(ApiConstants.HabitList.UPDATE_HABIT_LIST, id)
                .then()
                .statusCode(200);
        //formatter:on
    }

    public HabitListDTO getHabitList()throws JsonProcessingException{
        //@formatter:off
        String json = authorized()
                .when()
                .get(ApiConstants.HabitList.GET_HABIT_LIST)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        return objectMapper.readValue(json, HabitListDTO.class);
    }
}
