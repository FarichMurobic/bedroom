/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.controller;

import com.bedroom.application.identitas.command.GantiNamaPenggunaCommand;
import com.bedroom.application.identitas.result.PenggunaResult;
import com.bedroom.application.identitas.service.GantiNamaPenggunaApplicationService;
import com.bedroom.infrastructure.web.dto.GantiNamaPenggunaRequest;
import com.bedroom.infrastructure.web.dto.PenggunaResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Controller untuk operasi penggantian nama pengguna.
 */
@RestController
@RequestMapping("/identitas/pengguna")
public class GantiNamaPenggunaController {

    private final GantiNamaPenggunaApplicationService gantiNamaPenggunaApplicationService;

    public GantiNamaPenggunaController(GantiNamaPenggunaApplicationService gantiNamaPenggunaApplicationService) {
        this.gantiNamaPenggunaApplicationService = Objects.requireNonNull(
                gantiNamaPenggunaApplicationService, "Ganti nama pengguna application service tidak boleh kosong"
        );
    }

    /**
     * Mengganti nama pengguna yang sedang terautentikasi.
     *
     * @param request request berisi nama pengguna baru
     * @return data pengguna yang telah diperbarui
     */
    @PatchMapping("/nama-pengguna")
    public ResponseEntity<PenggunaResponse> gantiNamaPengguna(@Valid @RequestBody GantiNamaPenggunaRequest request) {
        GantiNamaPenggunaCommand command = new GantiNamaPenggunaCommand(request.namaPenggunaBaru());

        PenggunaResult hasil = gantiNamaPenggunaApplicationService.execute(command);

        return ResponseEntity.ok(PenggunaResponse.dari(hasil));
    }
}