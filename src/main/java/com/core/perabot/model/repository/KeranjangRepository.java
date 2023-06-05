package com.core.perabot.model.repository;

import com.core.perabot.model.models.Keranjang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeranjangRepository extends JpaRepository<Keranjang, Long>, JpaSpecificationExecutor<Keranjang> {

    @Query("SELECT k FROM Keranjang k WHERE k.id_pembeli = :id_pembeli AND k.status_pesan = false")
    List<Keranjang> findById_pembeli(Long id_pembeli);

    @Query("SELECT SUM(k.sub_total) AS total FROM Keranjang k WHERE k.id_pembeli = :id_pembeli AND k.status_pesan = false")
    Integer getTotal(Long id_pembeli);

    @Query("SELECT k FROM Keranjang k WHERE k.id_keranjang = :id_keranjang")
    Keranjang findById_keranjang(Long id_keranjang);

    @Query("SELECT COUNT(k) FROM Keranjang k WHERE k.id_pembeli = :id_pembeli AND k.status_pesan = false")
    Integer getJumlahBarangDalamKeranjang(Long id_pembeli);
}
