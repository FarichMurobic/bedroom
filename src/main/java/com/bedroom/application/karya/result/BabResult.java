/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.karya.result;

/**
 * Representasi data bab yang dikembalikan dari application service.
 */
public record BabResult(
        String id,
        String judul,
        String isi,
        int urutan
) {
}