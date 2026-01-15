package com.example.demo.dto;

import java.math.BigDecimal;
import java.util.List;

public class SalleDTO {
    private Integer idSalle;
    private String nomSalle;
    private Integer capacite;
    private Integer ligne;
    private Integer colonne;
    private TarifPlaceDTO tarifPlace;
    private BigDecimal valeurMaximale;
    private List<PlaceDTO> places;

    public SalleDTO() {}

    public SalleDTO(Integer idSalle, String nomSalle, Integer capacite, Integer ligne, Integer colonne, TarifPlaceDTO tarifPlace, BigDecimal valeurMaximale, List<PlaceDTO> places) {
        this.idSalle = idSalle;
        this.nomSalle = nomSalle;
        this.capacite = capacite;
        this.ligne = ligne;
        this.colonne = colonne;
        this.tarifPlace = tarifPlace;
        this.valeurMaximale = valeurMaximale;
        this.places = places;
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

    public TarifPlaceDTO getTarifPlace() {
        return tarifPlace;
    }

    public void setTarifPlace(TarifPlaceDTO tarifPlace) {
        this.tarifPlace = tarifPlace;
    }

    public BigDecimal getValeurMaximale() {
        return valeurMaximale;
    }

    public void setValeurMaximale(BigDecimal valeurMaximale) {
        this.valeurMaximale = valeurMaximale;
    }

    public List<PlaceDTO> getPlaces() {
        return places;
    }

    public void setPlaces(List<PlaceDTO> places) {
        this.places = places;
    }
}
