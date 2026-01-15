package com.example.demo.dto;

public class PlaceDTO {
    private Integer idPlace;
    private SalleDTO salle;
    private TypePlaceDTO typePlace;
    private Integer numeroLigne;
    private Integer numeroColonne;

    public PlaceDTO() {}

    public PlaceDTO(Integer idPlace, SalleDTO salle, TypePlaceDTO typePlace, Integer numeroLigne, Integer numeroColonne) {
        this.idPlace = idPlace;
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

    public SalleDTO getSalle() {
        return salle;
    }

    public void setSalle(SalleDTO salle) {
        this.salle = salle;
    }

    public TypePlaceDTO getTypePlace() {
        return typePlace;
    }

    public void setTypePlace(TypePlaceDTO typePlace) {
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

    // Champ pour indiquer si la place est occupée (utilisé pour l'affichage)
    private boolean occupee;

    public boolean isOccupee() {
        return occupee;
    }

    public void setOccupee(boolean occupee) {
        this.occupee = occupee;
    }
}
