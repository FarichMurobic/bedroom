package com.bedroom.infrastructure.web.controller;

import com.bedroom.application.identitas.command.GantiNamaPenggunaCommand;
import com.bedroom.application.identitas.service.GantiNamaPenggunaApplicationService;
import com.bedroom.domain.identitas.model.Pengguna;
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
 * Endpoint terautentikasi untuk mengganti nama pengguna milik akun
 * yang sedang login. Siapa pengguna yang login diambil dari konteks
 * keamanan (token JWT), bukan dari isi permintaan.
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

    @PatchMapping("/nama-pengguna")
    public ResponseEntity<PenggunaResponse> gantiNamaPengguna(@Valid @RequestBody GantiNamaPenggunaRequest request) {
        GantiNamaPenggunaCommand command = new GantiNamaPenggunaCommand(request.namaPenggunaBaru());

        Pengguna pengguna = gantiNamaPenggunaApplicationService.execute(command);

        return ResponseEntity.ok(PenggunaResponse.dari(pengguna));
    }
}