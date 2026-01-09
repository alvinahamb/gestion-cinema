package com.example.demo.dto;

import java.time.LocalDate;
import java.math.BigDecimal;

public class SeanceDTO {
    private Integer idSeance;
    private Integer idFilmVersion;
    private Integer idSalle;
    private String heureDebut;
    private String heureFin;
    private Boolean avecSousTitre;
    private LocalDate dateSeance;
    private BigDecimal tarif;
    private Integer idStatus;

    public SeanceDTO() {}

    public SeanceDTO(Integer idSeance, Integer idFilmVersion, Integer idSalle, String heureDebut,
                    String heureFin, Boolean avecSousTitre, LocalDate dateSeance, BigDecimal tarif, Integer idStatus) {
        this.idSeance = idSeance;
        this.idFilmVersion = idFilmVersion;
        this.idSalle = idSalle;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
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

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
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
