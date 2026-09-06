package com.bedroom.infrastructure.web.controller;

import com.bedroom.application.identitas.command.VerifikasiEmailCommand;
import com.bedroom.application.identitas.command.VerifikasiOtpCommand;
import com.bedroom.application.identitas.result.HasilAutentikasi;
import com.bedroom.application.identitas.service.VerifikasiApplicationService;
import com.bedroom.infrastructure.web.dto.HasilAutentikasiResponse;
import com.bedroom.infrastructure.web.dto.VerifikasiEmailRequest;
import com.bedroom.infrastructure.web.dto.VerifikasiOtpRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Endpoint publik untuk verifikasi akun pengguna, baik melalui tautan
 * konfirmasi email maupun kode OTP telepon. Verifikasi yang berhasil
 * langsung menyertakan token akses (auto-login).
 */
@RestController
@RequestMapping("/publik/identitas/verifikasi")
public class VerifikasiController {

    private final VerifikasiApplicationService verifikasiApplicationService;

    public VerifikasiController(VerifikasiApplicationService verifikasiApplicationService) {
        this.verifikasiApplicationService = Objects.requireNonNull(
                verifikasiApplicationService, "Verifikasi application service tidak boleh kosong"
        );
    }

    @PostMapping("/email")
    public ResponseEntity<HasilAutentikasiResponse> verifikasiEmail(@Valid @RequestBody VerifikasiEmailRequest request) {
        VerifikasiEmailCommand command = new VerifikasiEmailCommand(request.email(), request.token());

        HasilAutentikasi hasil = verifikasiApplicationService.verifikasiEmail(command);

        return ResponseEntity.ok(keResponse(hasil));
    }

    @PostMapping("/otp")
    public ResponseEntity<HasilAutentikasiResponse> verifikasiOtp(@Valid @RequestBody VerifikasiOtpRequest request) {
        VerifikasiOtpCommand command = new VerifikasiOtpCommand(request.nomorTelepon(), request.kodeOtp());

        HasilAutentikasi hasil = verifikasiApplicationService.verifikasiOtp(command);

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