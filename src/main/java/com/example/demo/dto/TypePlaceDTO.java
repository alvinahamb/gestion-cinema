package com.example.demo.dto;

public class TypePlaceDTO {
    private Integer idTypePlace;
    private String libelle;
    private String description;

    public TypePlaceDTO() {}

    public TypePlaceDTO(Integer idTypePlace, String libelle, String description) {
        this.idTypePlace = idTypePlace;
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
}
