package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "seance")
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSeance;
    
    @ManyToOne
    @JoinColumn(name = "id_film_version")
    private FilmVersion filmVersion;
    
    @ManyToOne
    @JoinColumn(name = "id_salle")
    private Salle salle;
    
    private LocalDateTime heureDebut;
    
    private LocalDateTime heureFin;
    
    private Boolean avecSousTitre;
    
    private LocalDate dateSeance;
    
    private BigDecimal tarif;
    
    @ManyToOne
    @JoinColumn(name = "id_status")
    private StatusSeance statusSeance;

    public Seance() {}

    public Seance(FilmVersion filmVersion, Salle salle, LocalDateTime heureDebut, 
                  LocalDateTime heureFin, Boolean avecSousTitre, LocalDate dateSeance, 
                  BigDecimal tarif, StatusSeance statusSeance) {
        this.filmVersion = filmVersion;
        this.salle = salle;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.avecSousTitre = avecSousTitre;
        this.dateSeance = dateSeance;
        this.tarif = tarif;
        this.statusSeance = statusSeance;
    }

    public Integer getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(Integer idSeance) {
        this.idSeance = idSeance;
    }

    public FilmVersion getFilmVersion() {
        return filmVersion;
    }

    public void setFilmVersion(FilmVersion filmVersion) {
        this.filmVersion = filmVersion;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public LocalDateTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalDateTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalDateTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalDateTime heureFin) {
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

    public StatusSeance getStatusSeance() {
        return statusSeance;
    }

    public void setStatusSeance(StatusSeance statusSeance) {
        this.statusSeance = statusSeance;
    }
}
