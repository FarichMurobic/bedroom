/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.interaksi.command;

/**
 * Perintah untuk membatalkan like pada sebuah karya.
 */
public record BatalSukaKaryaCommand(String idKarya) {
}