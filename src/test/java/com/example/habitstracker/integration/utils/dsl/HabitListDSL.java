package com.example.habitstracker.integration.utils.dsl;

import com.example.habitstracker.constants.ApiConstants;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.services.MapperService;
import com.example.openapi.dto.HabitDTO;
import com.example.openapi.dto.HabitListDTO;
import com.example.openapi.dto.NameDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.habitstracker.integration.utils.dsl.DSLHelper.authorized;

@Component
public class HabitListDSL {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MapperService mapperService;

    public void updateHabitList(String name) throws JsonProcessingException {
        var nameDTO = new NameDTO();
        nameDTO.setName(name);

        // @formatter:off
        authorized()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(nameDTO))
            .when()
                .put(ApiConstants.HabitList.UPDATE_HABIT_LIST)
            .then()
                .statusCode(200);
        // @formatter:on
    }

    public HabitListDTO getHabitList() throws JsonProcessingException{

        //@formatter:off
       String json = authorized()
               .when()
                    .get(ApiConstants.HabitList.GET_HABIT_LIST)
               //.then()
                    .body()
                    .asString();
        // @formatter:on

        return objectMapper.readValue(json, HabitListDTO.class);
    }

}
