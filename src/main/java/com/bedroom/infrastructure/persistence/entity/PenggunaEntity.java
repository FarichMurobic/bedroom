package com.bedroom.infrastructure.persistence.entity;

import com.bedroom.domain.identitas.enums.Peran;
import com.bedroom.domain.identitas.enums.StatusPengguna;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

/**
 * Representasi persistence dari entity domain {@code Pengguna}.
 * Dipisahkan dari model domain agar domain tetap bebas dari
 * dependensi terhadap Jakarta Persistence/Hibernate.
 */
@Entity
@Table(
        name = "pengguna",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_pengguna_nama_pengguna",
                        columnNames = "nama_pengguna")
        }
)
public class PenggunaEntity {

    @Id
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    private UUID id;

    @Column(
            name = "nama_pengguna",
            nullable = false,
            length = 30
    )
    private String namaPengguna;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    private StatusPengguna status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "pengguna_peran",
            joinColumns = @JoinColumn(
                    name = "pengguna_id",
                    foreignKey = @ForeignKey(
                            name = "fk_pengguna_peran_pengguna"
                    )
            )
    )
    @Enumerated(EnumType.STRING)
    @Column(
            name = "peran",
            nullable = false,
            length = 20
    )
    private Set<Peran> perans = EnumSet.noneOf(Peran.class);

    @Column(
            name = "dibuat_pada",
            nullable = false,
            updatable = false
    )
    private Instant dibuatPada;

    @Column(
            name = "diperbarui_pada",
            nullable = false
    )
    private Instant diperbaruiPada;

    protected PenggunaEntity() {
        // Untuk JPA/Hibernate
    }

    public PenggunaEntity(
            UUID id,
            String namaPengguna,
            StatusPengguna status,
            Set<Peran> perans,
            Instant dibuatPada,
            Instant diperbaruiPada
    ) {
        this.id = id;
        this.namaPengguna = namaPengguna;
        this.status = status;
        this.perans = perans;
        this.dibuatPada = dibuatPada;
        this.diperbaruiPada = diperbaruiPada;
    }

    public UUID getId() {
        return id;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public StatusPengguna getStatus() {
        return status;
    }

    public Set<Peran> getPerans() {
        return perans;
    }

    public Instant getDibuatPada() {
        return dibuatPada;
    }

    public Instant getDiperbaruiPada() {
        return diperbaruiPada;
    }
}
