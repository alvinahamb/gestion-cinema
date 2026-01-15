package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "type_place")
public class TypePlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTypePlace;
    
    @Column(nullable = false, length = 100)
    private String libelle;
    
    @Column(columnDefinition = "TEXT")
    private String description;

    public TypePlace() {}

    public TypePlace(String libelle, String description) {
        this.libelle = libelle;
        this.description = description;
    }

    public Integer getIdTypePlace() {
        return idTypePlace;
    }

    public void setIdTypePlace(Integer idTypePlace) {
        this.idTypePlace = idTypePlace;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TypePlace{" +
                "idTypePlace=" + idTypePlace +
                ", libelle='" + libelle + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
