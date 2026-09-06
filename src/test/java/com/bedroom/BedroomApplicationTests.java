/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Smoke test: memastikan seluruh application context Spring dapat
 * dimuat tanpa error, termasuk seluruh bean domain, application,
 * dan infrastructure yang telah dibangun.
 */
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("BedroomApplication")
class BedroomApplicationTests {

    @Test
    @DisplayName("application context berhasil dimuat")
    void contextLoads() {
        // Jika method ini berjalan tanpa exception, berarti seluruh bean
        // (repository, service, security config, dst) berhasil di-wire.
    }
}