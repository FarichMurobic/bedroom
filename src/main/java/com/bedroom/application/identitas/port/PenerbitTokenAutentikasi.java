/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.application.identitas.port;

import com.bedroom.domain.identitas.valueobject.IdPengguna;

/**
 * Port untuk menerbitkan token autentikasi (mis. JWT) bagi pengguna
 * yang berhasil melewati proses login.
 */
public interface PenerbitTokenAutentikasi {

    String terbitkan(IdPengguna idPengguna);
}