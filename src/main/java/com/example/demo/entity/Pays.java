package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pays")
public class Pays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPays;
    
    @Column(nullable = false)
    private String nom;

    public Pays() {}

    public Pays(String nom) {
        this.nom = nom;
    }

    public Integer getIdPays() {
        return idPays;
    }

    public void setIdPays(Integer idPays) {
        this.idPays = idPays;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
