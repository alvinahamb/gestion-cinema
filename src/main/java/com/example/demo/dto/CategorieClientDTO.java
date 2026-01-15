package com.example.demo.dto;

public class CategorieClientDTO {
    private Integer idCategorieClient;
    private String libelle;
    private String description;

    public CategorieClientDTO() {}

    public CategorieClientDTO(Integer idCategorieClient, String libelle, String description) {
        this.idCategorieClient = idCategorieClient;
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