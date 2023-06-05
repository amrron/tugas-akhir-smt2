package com.core.perabot.model.repository;

import com.core.perabot.model.models.Kategori;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KategoriRepository extends JpaRepository <Kategori, String> {
    @Query("SELECT k FROM Kategori k ORDER BY k.nama_kategori ASC")
    List<Kategori> findAll();
}