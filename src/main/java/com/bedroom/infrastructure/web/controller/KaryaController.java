/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.controller;

import com.bedroom.application.karya.command.ArsipkanKaryaCommand;
import com.bedroom.application.karya.command.GantiJudulKaryaCommand;
import com.bedroom.application.karya.command.GantiSinopsisKaryaCommand;
import com.bedroom.application.karya.command.HapusBabCommand;
import com.bedroom.application.karya.command.HapusGenreDariKaryaCommand;
import com.bedroom.application.karya.command.MulaiTulisKaryaCommand;
import com.bedroom.application.karya.command.PulihkanDariArsipCommand;
import com.bedroom.application.karya.command.TambahBabCommand;
import com.bedroom.application.karya.command.TambahGenreKeKaryaCommand;
import com.bedroom.application.karya.command.TarikKeDraftCommand;
import com.bedroom.application.karya.command.TerbitkanKaryaCommand;
import com.bedroom.application.karya.command.UbahIsiBabCommand;
import com.bedroom.application.karya.command.UbahJudulBabCommand;
import com.bedroom.application.karya.result.KaryaResult;
import com.bedroom.application.karya.service.PenerbitanKaryaApplicationService;
import com.bedroom.application.karya.service.PenjelajahanKaryaApplicationService;
import com.bedroom.application.karya.service.PenulisanBabApplicationService;
import com.bedroom.application.karya.service.PenulisanKaryaApplicationService;
import com.bedroom.infrastructure.web.dto.karya.DaftarKaryaResponse;
import com.bedroom.infrastructure.web.dto.karya.GantiJudulRequest;
import com.bedroom.infrastructure.web.dto.karya.GantiSinopsisRequest;
import com.bedroom.infrastructure.web.dto.karya.KaryaResponse;
import com.bedroom.infrastructure.web.dto.karya.MulaiTulisKaryaRequest;
import com.bedroom.infrastructure.web.dto.karya.TambahBabRequest;
import com.bedroom.infrastructure.web.dto.karya.UbahIsiBabRequest;
import com.bedroom.infrastructure.web.dto.karya.UbahJudulBabRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/karya")
public class KaryaController {

    private final PenulisanKaryaApplicationService penulisanKaryaApplicationService;
    private final PenulisanBabApplicationService penulisanBabApplicationService;
    private final PenerbitanKaryaApplicationService penerbitanKaryaApplicationService;
    private final PenjelajahanKaryaApplicationService penjelajahanKaryaApplicationService;

    public KaryaController(
            PenulisanKaryaApplicationService penulisanKaryaApplicationService,
            PenulisanBabApplicationService penulisanBabApplicationService,
            PenerbitanKaryaApplicationService penerbitanKaryaApplicationService,
            PenjelajahanKaryaApplicationService penjelajahanKaryaApplicationService
    ) {
        this.penulisanKaryaApplicationService = Objects.requireNonNull(penulisanKaryaApplicationService, "Penulisan karya application service tidak boleh kosong");
        this.penulisanBabApplicationService = Objects.requireNonNull(penulisanBabApplicationService, "Penulisan bab application service tidak boleh kosong");
        this.penerbitanKaryaApplicationService = Objects.requireNonNull(penerbitanKaryaApplicationService, "Penerbitan karya application service tidak boleh kosong");
        this.penjelajahanKaryaApplicationService = Objects.requireNonNull(penjelajahanKaryaApplicationService, "Penjelajahan karya application service tidak boleh kosong");
    }

    @GetMapping
    public ResponseEntity<DaftarKaryaResponse> daftarKaryaTerbit() {
        return ResponseEntity.ok(DaftarKaryaResponse.dari(penjelajahanKaryaApplicationService.daftarKaryaTerbit()));
    }

    @GetMapping("/{idKarya}")
    public ResponseEntity<KaryaResponse> detailKarya(@PathVariable String idKarya) {
        KaryaResult hasil = penjelajahanKaryaApplicationService.detailKarya(idKarya);
        return ResponseEntity.ok(KaryaResponse.dari(hasil));
    }

    @PostMapping
    public ResponseEntity<KaryaResponse> mulaiTulis(@Valid @RequestBody MulaiTulisKaryaRequest request) {
        KaryaResult hasil = penulisanKaryaApplicationService.mulaiTulis(
                new MulaiTulisKaryaCommand(request.judul(), request.sinopsis())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(KaryaResponse.dari(hasil));
    }

    @PatchMapping("/{idKarya}/judul")
    public ResponseEntity<KaryaResponse> gantiJudul(
            @PathVariable String idKarya, @Valid @RequestBody GantiJudulRequest request
    ) {
        KaryaResult hasil = penulisanKaryaApplicationService.gantiJudul(
                new GantiJudulKaryaCommand(idKarya, request.judulBaru())
        );
        return ResponseEntity.ok(KaryaResponse.dari(hasil));
    }

    @PatchMapping("/{idKarya}/sinopsis")
    public ResponseEntity<KaryaResponse> gantiSinopsis(
            @PathVariable String idKarya, @Valid @RequestBody GantiSinopsisRequest request
    ) {
        KaryaResult hasil = penulisanKaryaApplicationService.gantiSinopsis(
                new GantiSinopsisKaryaCommand(idKarya, request.sinopsisBaru())
        );
        return ResponseEntity.ok(KaryaResponse.dari(hasil));
    }

    @PostMapping("/{idKarya}/genre/{idGenre}")
    public ResponseEntity<KaryaResponse> tambahGenre(@PathVariable String idKarya, @PathVariable String idGenre) {
        KaryaResult hasil = penulisanKaryaApplicationService.tambahGenre(
                new TambahGenreKeKaryaCommand(idKarya, idGenre)
        );
        return ResponseEntity.ok(KaryaResponse.dari(hasil));
    }

    @DeleteMapping("/{idKarya}/genre/{idGenre}")
    public ResponseEntity<KaryaResponse> hapusGenre(@PathVariable String idKarya, @PathVariable String idGenre) {
        KaryaResult hasil = penulisanKaryaApplicationService.hapusGenre(
                new HapusGenreDariKaryaCommand(idKarya, idGenre)
        );
        return ResponseEntity.ok(KaryaResponse.dari(hasil));
    }

    @PostMapping("/{idKarya}/bab")
    public ResponseEntity<KaryaResponse> tambahBab(
            @PathVariable String idKarya, @Valid @RequestBody TambahBabRequest request
    ) {
        KaryaResult hasil = penulisanBabApplicationService.tambahBab(
                new TambahBabCommand(idKarya, request.judulBab(), request.isiBab())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(KaryaResponse.dari(hasil));
    }

    @PatchMapping("/{idKarya}/bab/{idBab}/judul")
    public ResponseEntity<KaryaResponse> ubahJudulBab(
            @PathVariable String idKarya, @PathVariable String idBab, @Valid @RequestBody UbahJudulBabRequest request
    ) {
        KaryaResult hasil = penulisanBabApplicationService.ubahJudulBab(
                new UbahJudulBabCommand(idKarya, idBab, request.judulBaru())
        );
        return ResponseEntity.ok(KaryaResponse.dari(hasil));
    }

    @PatchMapping("/{idKarya}/bab/{idBab}/isi")
    public ResponseEntity<KaryaResponse> ubahIsiBab(
            @PathVariable String idKarya, @PathVariable String idBab, @Valid @RequestBody UbahIsiBabRequest request
    ) {
        KaryaResult hasil = penulisanBabApplicationService.ubahIsiBab(
                new UbahIsiBabCommand(idKarya, idBab, request.isiBaru())
        );
        return ResponseEntity.ok(KaryaResponse.dari(hasil));
    }

    @DeleteMapping("/{idKarya}/bab/{idBab}")
    public ResponseEntity<KaryaResponse> hapusBab(@PathVariable String idKarya, @PathVariable String idBab) {
        KaryaResult hasil = penulisanBabApplicationService.hapusBab(new HapusBabCommand(idKarya, idBab));
        return ResponseEntity.ok(KaryaResponse.dari(hasil));
    }

    @PostMapping("/{idKarya}/terbitkan")
    public ResponseEntity<KaryaResponse> terbitkan(@PathVariable String idKarya) {
        KaryaResult hasil = penerbitanKaryaApplicationService.terbitkan(new TerbitkanKaryaCommand(idKarya));
        return ResponseEntity.ok(KaryaResponse.dari(hasil));
    }

    @PostMapping("/{idKarya}/tarik-draft")
    public ResponseEntity<KaryaResponse> tarikKeDraft(@PathVariable String idKarya) {
        KaryaResult hasil = penerbitanKaryaApplicationService.tarikKeDraft(new TarikKeDraftCommand(idKarya));
        return ResponseEntity.ok(KaryaResponse.dari(hasil));
    }

    @PostMapping("/{idKarya}/arsipkan")
    public ResponseEntity<KaryaResponse> arsipkan(@PathVariable String idKarya) {
        KaryaResult hasil = penerbitanKaryaApplicationService.arsipkan(new ArsipkanKaryaCommand(idKarya));
        return ResponseEntity.ok(KaryaResponse.dari(hasil));
    }

    @PostMapping("/{idKarya}/pulihkan")
    public ResponseEntity<KaryaResponse> pulihkanDariArsip(@PathVariable String idKarya) {
        KaryaResult hasil = penerbitanKaryaApplicationService.pulihkanDariArsip(new PulihkanDariArsipCommand(idKarya));
        return ResponseEntity.ok(KaryaResponse.dari(hasil));
    }
}