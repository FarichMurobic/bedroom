/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.controller;

import com.bedroom.application.identitas.command.LengkapiProfilCommand;
import com.bedroom.application.identitas.result.ProfilResult;
import com.bedroom.application.identitas.service.ProfilApplicationService;
import com.bedroom.infrastructure.web.dto.LengkapiProfilRequest;
import com.bedroom.infrastructure.web.dto.ProfilResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Endpoint terautentikasi untuk melihat dan melengkapi profil pengguna
 * yang sedang login.
 */
@RestController
@RequestMapping("/identitas/profil")
public class ProfilController {

    private final ProfilApplicationService profilApplicationService;

    public ProfilController(ProfilApplicationService profilApplicationService) {
        this.profilApplicationService = Objects.requireNonNull(profilApplicationService, "Profil application service tidak boleh kosong");
    }

    /**
     * Melihat profil pengguna yang sedang terautentikasi.
     *
     * @return data profil pengguna
     */
    @GetMapping
    public ResponseEntity<ProfilResponse> lihatProfilSaya() {
        ProfilResult hasil = profilApplicationService.lihatProfilSaya();
        return ResponseEntity.ok(ProfilResponse.dari(hasil));
    }

    /**
     * Melengkapi atau memperbarui data profil pengguna yang sedang terautentikasi.
     *
     * @param request request lengkapi profil
     * @return data profil yang telah diperbarui
     */
    @PatchMapping
    public ResponseEntity<ProfilResponse> lengkapiProfil(@RequestBody LengkapiProfilRequest request) {
        LengkapiProfilCommand command = new LengkapiProfilCommand(
                request.namaLengkap(), request.tanggalLahir(), request.jenisKelamin(), request.urlAvatar()
        );
        ProfilResult hasil = profilApplicationService.lengkapiProfil(command);
        return ResponseEntity.ok(ProfilResponse.dari(hasil));
    }
}