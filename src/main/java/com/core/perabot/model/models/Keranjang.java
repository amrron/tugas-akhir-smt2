package com.core.perabot.model.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "keranjang")
public class Keranjang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_keranjang")
    private Long id_keranjang;

    @Column(name = "id_pembeli")
    private Long id_pembeli;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_barang", referencedColumnName = "id_barang")
    private Barang id_barang;

    @Column(name = "jumlah")
    private Integer jumlah;

    @Column(name = "sub_total")
    private Integer sub_total;

    @Column(name = "status_pesan")
    private Boolean status_pesan;
}
