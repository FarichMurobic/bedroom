/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.controller;

import com.bedroom.application.interaksi.command.BatalSukaKaryaCommand;
import com.bedroom.application.interaksi.command.SukaKaryaCommand;
import com.bedroom.application.interaksi.result.StatusSukaResult;
import com.bedroom.application.interaksi.service.SukaApplicationService;
import com.bedroom.infrastructure.web.dto.karya.StatusSukaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Endpoint terautentikasi untuk menyukai dan membatalkan suka pada karya.
 */
@RestController
@RequestMapping("/karya/{idKarya}/suka")
public class SukaController {

    private final SukaApplicationService sukaApplicationService;

    public SukaController(SukaApplicationService sukaApplicationService) {
        this.sukaApplicationService = Objects.requireNonNull(sukaApplicationService, "Suka application service tidak boleh kosong");
    }

    /**
     * Memberikan like pada sebuah karya.
     *
     * @param idKarya ID karya
     * @return status like terbaru
     */
    @PostMapping
    public ResponseEntity<StatusSukaResponse> sukai(@PathVariable String idKarya) {
        StatusSukaResult hasil = sukaApplicationService.sukai(new SukaKaryaCommand(idKarya));
        return ResponseEntity.ok(StatusSukaResponse.dari(hasil));
    }

    /**
     * Membatalkan like pada sebuah karya.
     *
     * @param idKarya ID karya
     * @return status like terbaru
     */
    @DeleteMapping
    public ResponseEntity<StatusSukaResponse> batalSukai(@PathVariable String idKarya) {
        StatusSukaResult hasil = sukaApplicationService.batalSukai(new BatalSukaKaryaCommand(idKarya));
        return ResponseEntity.ok(StatusSukaResponse.dari(hasil));
    }
}