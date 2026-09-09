/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.controller;

import com.bedroom.application.karya.command.BuatGenreCommand;
import com.bedroom.application.karya.result.GenreResult;
import com.bedroom.application.karya.service.GenreApplicationService;
import com.bedroom.application.karya.service.PenjelajahanKaryaApplicationService;
import com.bedroom.infrastructure.web.dto.karya.BuatGenreRequest;
import com.bedroom.infrastructure.web.dto.karya.DaftarGenreResponse;
import com.bedroom.infrastructure.web.dto.karya.GenreResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/genre")
public class GenreController {

    private final GenreApplicationService genreApplicationService;
    private final PenjelajahanKaryaApplicationService penjelajahanKaryaApplicationService;

    public GenreController(
            GenreApplicationService genreApplicationService,
            PenjelajahanKaryaApplicationService penjelajahanKaryaApplicationService
    ) {
        this.genreApplicationService = Objects.requireNonNull(genreApplicationService, "Genre application service tidak boleh kosong");
        this.penjelajahanKaryaApplicationService = Objects.requireNonNull(penjelajahanKaryaApplicationService, "Penjelajahan karya application service tidak boleh kosong");
    }

    @GetMapping
    public ResponseEntity<DaftarGenreResponse> daftarGenre() {
        return ResponseEntity.ok(DaftarGenreResponse.dari(penjelajahanKaryaApplicationService.daftarGenre()));
    }

    @PostMapping
    public ResponseEntity<GenreResponse> buatGenre(@Valid @RequestBody BuatGenreRequest request) {
        GenreResult hasil = genreApplicationService.buatGenre(new BuatGenreCommand(request.nama()));
        return ResponseEntity.status(HttpStatus.CREATED).body(GenreResponse.dari(hasil));
    }
}