package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categorie_client")
public class CategorieClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategorieClient;

    @Column(nullable = false, length = 100)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    public CategorieClient() {}

    public CategorieClient(String libelle, String description) {
        this.libelle = libelle;
        this.description = description;
    }

    public Integer getIdCategorieClient() {
        return idCategorieClient;
    }

    public void setIdCategorieClient(Integer idCategorieClient) {
        this.idCategorieClient = idCategorieClient;
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
}