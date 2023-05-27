package com.core.perabot.model.repository;

import com.core.perabot.model.models.Barang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BarangRepository extends JpaRepository <Barang, Long> {
    @Query("SELECT b FROM Barang b WHERE b.status_aktif = :status")
    List<Barang> findAllBy(Boolean status);
}
