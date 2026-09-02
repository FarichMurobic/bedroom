package com.bedroom.infrastructure.persistence;

import com.bedroom.infrastructure.security.SecurityTestKeyConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(SecurityTestKeyConfiguration.class)
class FlywayIntegrationTest {

    @Test
    void contextLoadsWithFlyway() {
    }
}