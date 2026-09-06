/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.infrastructure.web.dto;

import java.time.Instant;
import java.util.List;

/**
 * Format respons error yang konsisten untuk seluruh API Bedroom.
 */
public record ErrorResponse(
        Instant waktu,
        int statusKode,
        String pesan,
        List<String> detail
) {
    public ErrorResponse(int statusKode, String pesan) {
        this(Instant.now(), statusKode, pesan, List.of());
    }

    public ErrorResponse(int statusKode, String pesan, List<String> detail) {
        this(Instant.now(), statusKode, pesan, detail);
    }
}