package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "type_statut")
public class TypeStatut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTypeStatut;

    @Column(nullable = false, length = 100)
    private String libelle;

    public TypeStatut() {}

    public TypeStatut(String libelle) {
        this.libelle = libelle;
    }

    public Integer getIdTypeStatut() {
        return idTypeStatut;
    }

    public void setIdTypeStatut(Integer idTypeStatut) {
        this.idTypeStatut = idTypeStatut;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}