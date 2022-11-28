package com.example.habitstracker.integration.tests;

import com.example.habitstracker.integration.utils.TestUserBuilder;
import com.example.habitstracker.models.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserOperationTest extends AbstractIntegrationTest{

    @Autowired
    private ObjectMapper objectMapper;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        super.setup();
        user = new TestUserBuilder().build();
    }

    @Test
    void getUserTest(){

    }
}
