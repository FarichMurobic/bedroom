/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.karya.result;

/**
 * Representasi data genre yang dikembalikan dari application service.
 */
public record GenreResult(
        String id,
        String nama
) {
}