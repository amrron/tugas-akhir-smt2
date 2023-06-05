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
@Table(name = "pembeli")
public class Pembeli {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pembeli")
    private Long id_pembeli;

    @Column(name = "nama_pembeli")
    private String nama_pembeli;

    @Column(name = "no_hp")
    private String no_hp;

    @Column(name = "password")
    private String password;
}
