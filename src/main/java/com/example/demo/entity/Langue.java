package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "langue")
public class Langue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLangue;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String code;

    public Langue() {}

    public Langue(String nom, String code) {
        this.nom = nom;
        this.code = code;
    }

    public Integer getIdLangue() {
        return idLangue;
    }

    public void setIdLangue(Integer idLangue) {
        this.idLangue = idLangue;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
