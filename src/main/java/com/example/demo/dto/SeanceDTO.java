package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.math.BigDecimal;

public class SeanceDTO {
    private Integer idSeance;
    private FilmVersionDTO filmVersion;
    private SalleDTO salle;
    private LocalTime heureDebut;
    private Boolean avecSousTitre;
    private LocalDate dateSeance;
    private BigDecimal tarif;
    private StatusDTO status;
    private BigDecimal valeurMaximale;
    private BigDecimal chiffreAffaire;

    public SeanceDTO() {}

    public SeanceDTO(Integer idSeance, FilmVersionDTO filmVersion, SalleDTO salle, LocalTime heureDebut,
                    Boolean avecSousTitre, LocalDate dateSeance, BigDecimal tarif, StatusDTO status, BigDecimal valeurMaximale) {
        this.idSeance = idSeance;
        this.filmVersion = filmVersion;
        this.salle = salle;
        this.heureDebut = heureDebut;
        this.avecSousTitre = avecSousTitre;
        this.dateSeance = dateSeance;
        this.tarif = tarif;
        this.status = status;
        this.valeurMaximale = valeurMaximale;
    }

    public Integer getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(Integer idSeance) {
        this.idSeance = idSeance;
    }

    public FilmVersionDTO getFilmVersion() {
        return filmVersion;
    }

    public void setFilmVersion(FilmVersionDTO filmVersion) {
        this.filmVersion = filmVersion;
    }

    public SalleDTO getSalle() {
        return salle;
    }

    public void setSalle(SalleDTO salle) {
        this.salle = salle;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Boolean getAvecSousTitre() {
        return avecSousTitre;
    }

    public void setAvecSousTitre(Boolean avecSousTitre) {
        this.avecSousTitre = avecSousTitre;
    }

    public LocalDate getDateSeance() {
        return dateSeance;
    }

    public void setDateSeance(LocalDate dateSeance) {
        this.dateSeance = dateSeance;
    }

    public BigDecimal getTarif() {
        return tarif;
    }

    public void setTarif(BigDecimal tarif) {
        this.tarif = tarif;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public BigDecimal getValeurMaximale() {
        return valeurMaximale;
    }

    public void setValeurMaximale(BigDecimal valeurMaximale) {
        this.valeurMaximale = valeurMaximale;
    }

    public BigDecimal getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(BigDecimal chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }
}
