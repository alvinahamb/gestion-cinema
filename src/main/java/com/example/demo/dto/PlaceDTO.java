package com.example.demo.dto;

import java.math.BigDecimal;

public class PlaceDTO {
    private Integer idPlace;
    private Integer idSalle;
    private Integer idTypePlace;
    private Integer numeroLigne;
    private Integer numeroColonne;
    private BigDecimal prixPlace;

    public PlaceDTO() {}

    public PlaceDTO(Integer idPlace, Integer idSalle, Integer idTypePlace, Integer numeroLigne, Integer numeroColonne, BigDecimal prixPlace) {
        this.idPlace = idPlace;
        this.idSalle = idSalle;
        this.idTypePlace = idTypePlace;
        this.numeroLigne = numeroLigne;
        this.numeroColonne = numeroColonne;
        this.prixPlace = prixPlace;
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

    public Integer getIdTypePlace() {
        return idTypePlace;
    }

    public void setIdTypePlace(Integer idTypePlace) {
        this.idTypePlace = idTypePlace;
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

    public BigDecimal getPrixPlace() {
        return prixPlace;
    }

    public void setPrixPlace(BigDecimal prixPlace) {
        this.prixPlace = prixPlace;
    }

    // Champ pour indiquer si la place est occupée (utilisé pour l'affichage)
    private boolean occupee;

    public boolean isOccupee() {
        return occupee;
    }

    public void setOccupee(boolean occupee) {
        this.occupee = occupee;
    }
}
