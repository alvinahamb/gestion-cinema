package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "salle")
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSalle;
    
    @Column(nullable = false)
    private String nomSalle;
    
    @Column(nullable = false)
    private Integer capacite;

    public Salle() {}

    public Salle(String nomSalle, Integer capacite) {
        this.nomSalle = nomSalle;
        this.capacite = capacite;
    }

    public Integer getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(Integer idSalle) {
        this.idSalle = idSalle;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }
}
