/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.dto.karya;

import com.bedroom.application.karya.result.KaryaResult;
import com.bedroom.shared.pagination.HalamanResult;

import java.util.List;

public record DaftarKaryaResponse(
        List<KaryaResponse> isi,
        int halaman,
        int ukuranHalaman,
        long totalItem,
        int totalHalaman
) {
    public static DaftarKaryaResponse dari(HalamanResult<KaryaResult> hasil) {
        List<KaryaResponse> isiResponse = hasil.isi().stream().map(KaryaResponse::dari).toList();
        return new DaftarKaryaResponse(
                isiResponse, hasil.halaman(), hasil.ukuranHalaman(), hasil.totalItem(), hasil.totalHalaman()
        );
    }

    /**
     * Membungkus daftar tanpa paginasi (mis. daftar karya milik sendiri,
     * yang jumlahnya wajar tidak sebesar daftar publik).
     */
    public static DaftarKaryaResponse tanpaPaginasi(List<KaryaResult> hasilList) {
        List<KaryaResponse> isiResponse = hasilList.stream().map(KaryaResponse::dari).toList();
        int totalHalaman = isiResponse.isEmpty() ? 0 : 1;
        return new DaftarKaryaResponse(isiResponse, 0, isiResponse.size(), isiResponse.size(), totalHalaman);
    }
}