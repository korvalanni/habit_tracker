package com.example.habitstracker.integration.tests;

import com.example.habitstracker.integration.utils.CleanerService;
import com.example.habitstracker.integration.utils.dsl.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * Базовый класс для интеграционных тестов
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {
    @Autowired
    protected AuthDSL authDSL;
    @Autowired
    protected HabitDSL habitDSL;
    @Autowired
    protected UserDSL userDSL;

    @Autowired
    protected HabitListDSL habitListDSL;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    protected void setup() {
        RestAssured.port = port;
    }

    @AfterEach
    public void afterTest() {
        CleanerService.clean();
        DSLHelper.clean();
    }
}
