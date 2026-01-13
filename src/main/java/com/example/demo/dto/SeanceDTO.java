package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.math.BigDecimal;

public class SeanceDTO {
    private Integer idSeance;
    private Integer idFilmVersion;
    private Integer idSalle;
    private LocalTime heureDebut;
    private Boolean avecSousTitre;
    private LocalDate dateSeance;
    private BigDecimal tarif;
    private Integer idStatus;

    public SeanceDTO() {}

    public SeanceDTO(Integer idSeance, Integer idFilmVersion, Integer idSalle, LocalTime heureDebut,
                    Boolean avecSousTitre, LocalDate dateSeance, BigDecimal tarif, Integer idStatus) {
        this.idSeance = idSeance;
        this.idFilmVersion = idFilmVersion;
        this.idSalle = idSalle;
        this.heureDebut = heureDebut;
        this.avecSousTitre = avecSousTitre;
        this.dateSeance = dateSeance;
        this.tarif = tarif;
        this.idStatus = idStatus;
    }

    public Integer getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(Integer idSeance) {
        this.idSeance = idSeance;
    }

    public Integer getIdFilmVersion() {
        return idFilmVersion;
    }

    public void setIdFilmVersion(Integer idFilmVersion) {
        this.idFilmVersion = idFilmVersion;
    }

    public Integer getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(Integer idSalle) {
        this.idSalle = idSalle;
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

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }
}
