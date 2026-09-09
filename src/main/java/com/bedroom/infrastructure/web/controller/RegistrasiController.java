/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.controller;

import com.bedroom.application.identitas.command.RegistrasiEmailCommand;
import com.bedroom.application.identitas.command.RegistrasiTeleponCommand;
import com.bedroom.application.identitas.result.HasilRegistrasi;
import com.bedroom.application.identitas.service.RegistrasiApplicationService;
import com.bedroom.infrastructure.web.dto.HasilRegistrasiResponse;
import com.bedroom.infrastructure.web.dto.RegistrasiEmailRequest;
import com.bedroom.infrastructure.web.dto.RegistrasiTeleponRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/registrasi")
public class RegistrasiController {

    private final RegistrasiApplicationService registrasiApplicationService;

    public RegistrasiController(RegistrasiApplicationService registrasiApplicationService) {
        this.registrasiApplicationService = Objects.requireNonNull(
                registrasiApplicationService, "Registrasi application service tidak boleh kosong"
        );
    }

    @PostMapping("/email")
    public ResponseEntity<HasilRegistrasiResponse> registrasiEmail(@Valid @RequestBody RegistrasiEmailRequest request) {
        RegistrasiEmailCommand command = new RegistrasiEmailCommand(
                request.namaPengguna(), request.email(), request.kataSandi()
        );
        HasilRegistrasi hasil = registrasiApplicationService.registrasiEmail(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(keResponse(hasil));
    }

    @PostMapping("/telepon")
    public ResponseEntity<HasilRegistrasiResponse> registrasiTelepon(@Valid @RequestBody RegistrasiTeleponRequest request) {
        RegistrasiTeleponCommand command = new RegistrasiTeleponCommand(
                request.namaPengguna(), request.nomorTelepon(), request.kataSandi()
        );
        HasilRegistrasi hasil = registrasiApplicationService.registrasiTelepon(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(keResponse(hasil));
    }

    private HasilRegistrasiResponse keResponse(HasilRegistrasi hasil) {
        return new HasilRegistrasiResponse(hasil.idPengguna().nilai().toString(), hasil.namaPengguna());
    }
}