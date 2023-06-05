package com.core.perabot.model.repository;

import com.core.perabot.model.models.Pembeli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Pembeli, Long> {

    @Query("SELECT p FROM Pembeli p WHERE p.nama_pembeli = :namaPembeli")
    List<Pembeli> findByNamaPembeli(String namaPembeli);

    @Query("SELECT p FROM Pembeli p WHERE p.no_hp = :no_hp")
    Pembeli findByNoHp(String no_hp);

    @Query("SELECT p FROM Pembeli p WHERE p.id_pembeli = :id_pembeli")
    Pembeli findById_pembeli(Long id_pembeli);
}
