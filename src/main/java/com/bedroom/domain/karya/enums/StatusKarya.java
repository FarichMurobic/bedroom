/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.karya.enums;

/**
 * Merepresentasikan status penerbitan sebuah {@code Karya}.
 *
 * <p>Status ini menunjukkan tahapan karya dalam siklus penerbitannya,
 * mulai dari masih dalam penyusunan, telah diterbitkan, hingga diarsipkan.</p>
 */
public enum StatusKarya {
    DRAFT,
    TERBIT,
    DIARSIPKAN
}