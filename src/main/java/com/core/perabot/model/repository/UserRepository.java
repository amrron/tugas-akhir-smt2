package com.core.perabot.model.repository;

import com.core.perabot.model.models.Pembeli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<Pembeli, Long> {

    @Query("SELECT u FROM Pembeli u WHERE u.nama_pembeli = :namaPembeli")
    List<Pembeli> findByNamaPembeli(String namaPembeli);
}
