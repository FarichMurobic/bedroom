/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.karya.service;

import com.bedroom.application.karya.result.BabResult;
import com.bedroom.application.karya.result.KaryaResult;
import com.bedroom.domain.karya.model.Bab;
import com.bedroom.domain.karya.model.Karya;
import com.bedroom.domain.karya.valueobject.IdGenre;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Menerjemahkan entity domain {@code Karya} ke {@code KaryaResult}
 * untuk dikembalikan dari application service ke lapisan luar.
 */
final class KaryaMapper {

    private KaryaMapper() {
    }

    static KaryaResult keResult(Karya karya) {
        List<BabResult> babResultList = karya.babList().stream()
                .map(KaryaMapper::keBabResult)
                .toList();

        Set<String> idGenreString = karya.idGenre().stream()
                .map(IdGenre::nilai)
                .map(Object::toString)
                .collect(Collectors.toSet());

        return new KaryaResult(
                karya.id().nilai().toString(),
                karya.idPenulis().nilai().toString(),
                karya.judul().nilai(),
                karya.sinopsis().nilai(),
                karya.status().name(),
                idGenreString,
                babResultList,
                karya.dibuatPada(),
                karya.diperbaruiPada()
        );
    }

    private static BabResult keBabResult(Bab bab) {
        return new BabResult(
                bab.id().nilai().toString(),
                bab.judul().nilai(),
                bab.isi().nilai(),
                bab.urutan().nilai()
        );
    }
}