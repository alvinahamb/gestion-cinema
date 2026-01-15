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
    
    @Column(nullable = false)
    private Integer ligne;
    
    @Column(nullable = false)
    private Integer colonne;

    public Salle() {}

    public Salle(String nomSalle, Integer capacite, Integer ligne, Integer colonne) {
        this.nomSalle = nomSalle;
        this.capacite = capacite;
        this.ligne = ligne;
        this.colonne = colonne;
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

    public Integer getLigne() {
        return ligne;
    }

    public void setLigne(Integer ligne) {
        this.ligne = ligne;
    }

    public Integer getColonne() {
        return colonne;
    }

    public void setColonne(Integer colonne) {
        this.colonne = colonne;
    }
}
