/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.port;

import com.bedroom.domain.identitas.valueobject.IdPengguna;

/**
 * Port untuk menerbitkan token autentikasi bagi pengguna
 * yang berhasil melewati proses login.
 *
 * <p>Interface ini mendefinisikan kontrak yang dibutuhkan lapisan aplikasi
 * tanpa bergantung pada mekanisme penerbitan token tertentu. Implementasi
 * konkret, seperti penerbitan JWT, disediakan oleh lapisan infrastruktur.</p>
 */
public interface PenerbitTokenAutentikasi {

    /**
     * Menerbitkan token autentikasi untuk pengguna tertentu.
     *
     * @param idPengguna ID pengguna yang telah berhasil diautentikasi
     * @return token autentikasi yang diterbitkan untuk pengguna
     */
    String terbitkan(IdPengguna idPengguna);
}