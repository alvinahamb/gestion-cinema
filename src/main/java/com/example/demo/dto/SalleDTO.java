package com.example.demo.dto;

public class SalleDTO {
    private Integer idSalle;
    private String nomSalle;
    private Integer capacite;
    private Integer ligne;
    private Integer colonne;

    public SalleDTO() {}

    public SalleDTO(Integer idSalle, String nomSalle, Integer capacite, Integer ligne, Integer colonne) {
        this.idSalle = idSalle;
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
