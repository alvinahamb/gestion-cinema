package com.example.demo.dto;

public class PlaceDTO {
    private Integer idPlace;
    private Integer idSalle;
    private Integer numeroLigne;
    private Integer numeroColonne;

    public PlaceDTO() {}

    public PlaceDTO(Integer idPlace, Integer idSalle, Integer numeroLigne, Integer numeroColonne) {
        this.idPlace = idPlace;
        this.idSalle = idSalle;
        this.numeroLigne = numeroLigne;
        this.numeroColonne = numeroColonne;
    }

    public Integer getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(Integer idPlace) {
        this.idPlace = idPlace;
    }

    public Integer getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(Integer idSalle) {
        this.idSalle = idSalle;
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
