package com.example.habitstracker;

import org.junit.jupiter.api.AfterEach;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Базовый класс для интеграционных тестов
 */
@SpringBootTest
public abstract class AbstractIntegrationTest {
    @AfterEach
    public void afterTest() {
        CleanerService.clean();
    }
}
