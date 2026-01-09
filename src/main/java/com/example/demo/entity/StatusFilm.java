package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "status_film")
public class StatusFilm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStatus;
    
    @Column(nullable = false)
    private String libelle;

    public StatusFilm() {}

    public StatusFilm(String libelle) {
        this.libelle = libelle;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
