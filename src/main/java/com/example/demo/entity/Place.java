package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "place")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPlace;
    
    @ManyToOne
    @JoinColumn(name = "id_salle")
    private Salle salle;
    
    @ManyToOne
    @JoinColumn(name = "id_type_place")
    private TypePlace typePlace;
    
    @Column(nullable = false)
    private Integer numeroLigne;
    
    @Column(nullable = false)
    private Integer numeroColonne;

    public Place() {}

    public Place(Salle salle, TypePlace typePlace, Integer numeroLigne, Integer numeroColonne) {
        this.salle = salle;
        this.typePlace = typePlace;
        this.numeroLigne = numeroLigne;
        this.numeroColonne = numeroColonne;
    }

    public Integer getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(Integer idPlace) {
        this.idPlace = idPlace;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public TypePlace getTypePlace() {
        return typePlace;
    }

    public void setTypePlace(TypePlace typePlace) {
        this.typePlace = typePlace;
    }

    public Integer getNumeroLigne() {
        return numeroLigne;
    }

    public void setNumeroLigne(Integer numeroLigne) {
        this.numeroLigne = numeroLigne;
    }

    public Integer getNumeroColonne() {
        return numeroColonne;
    }

    public void setNumeroColonne(Integer numeroColonne) {
        this.numeroColonne = numeroColonne;
    }
}
