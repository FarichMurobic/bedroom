/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.infrastructure.web.controller;

import com.bedroom.application.identitas.command.LoginEmailCommand;
import com.bedroom.application.identitas.command.LoginGoogleCommand;
import com.bedroom.application.identitas.command.LoginTeleponCommand;
import com.bedroom.application.identitas.result.HasilAutentikasi;
import com.bedroom.application.identitas.service.LoginApplicationService;
import com.bedroom.infrastructure.web.dto.HasilAutentikasiResponse;
import com.bedroom.infrastructure.web.dto.LoginEmailRequest;
import com.bedroom.infrastructure.web.dto.LoginGoogleRequest;
import com.bedroom.infrastructure.web.dto.LoginTeleponRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Endpoint publik untuk login pengguna melalui email, nomor telepon,
 * maupun akun Google.
 */
@RestController
@RequestMapping("/publik/identitas/login")
public class LoginController {

    private final LoginApplicationService loginApplicationService;

    public LoginController(LoginApplicationService loginApplicationService) {
        this.loginApplicationService = Objects.requireNonNull(
                loginApplicationService, "Login application service tidak boleh kosong"
        );
    }

    @PostMapping("/email")
    public ResponseEntity<HasilAutentikasiResponse> loginEmail(@Valid @RequestBody LoginEmailRequest request) {
        LoginEmailCommand command = new LoginEmailCommand(request.email(), request.kataSandi());
        HasilAutentikasi hasil = loginApplicationService.loginEmail(command);
        return ResponseEntity.ok(keResponse(hasil));
    }

    @PostMapping("/telepon")
    public ResponseEntity<HasilAutentikasiResponse> loginTelepon(@Valid @RequestBody LoginTeleponRequest request) {
        LoginTeleponCommand command = new LoginTeleponCommand(request.nomorTelepon(), request.kataSandi());
        HasilAutentikasi hasil = loginApplicationService.loginTelepon(command);
        return ResponseEntity.ok(keResponse(hasil));
    }

    @PostMapping("/google")
    public ResponseEntity<HasilAutentikasiResponse> loginGoogle(@Valid @RequestBody LoginGoogleRequest request) {
        LoginGoogleCommand command = new LoginGoogleCommand(request.idTokenGoogle());
        HasilAutentikasi hasil = loginApplicationService.loginGoogle(command);
        return ResponseEntity.ok(keResponse(hasil));
    }

    private HasilAutentikasiResponse keResponse(HasilAutentikasi hasil) {
        return new HasilAutentikasiResponse(
                hasil.idPengguna().nilai().toString(),
                hasil.tokenAkses(),
                hasil.apakahPenggunaBaru()
        );
    }
}