package com.example.demo.dto;

import java.time.LocalDate;

public class DiffusionSeanceDTO {
    private Integer id;
    private Integer idDiffusion;
    private Integer idSeance;
    private Integer nombre;
    private LocalDate dateAjout;
    
    // Informations complémentaires pour l'affichage
    private String titrePublicite;
    private String nomSociete;
    private String filmTitre;
    private String salleName;
    private LocalDate dateSeance;

    public DiffusionSeanceDTO() {}

    public DiffusionSeanceDTO(Integer id, Integer idDiffusion, Integer idSeance, LocalDate dateAjout) {
        this.id = id;
        this.idDiffusion = idDiffusion;
        this.idSeance = idSeance;
        this.dateAjout = dateAjout;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdDiffusion() {
        return idDiffusion;
    }

    public void setIdDiffusion(Integer idDiffusion) {
        this.idDiffusion = idDiffusion;
    }

    public Integer getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(Integer idSeance) {
        this.idSeance = idSeance;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Integer getNombre() {
        return nombre;
    }

    public void setNombre(Integer nombre) {
        this.nombre = nombre;
    }

    public String getTitrePublicite() {
        return titrePublicite;
    }

    public void setTitrePublicite(String titrePublicite) {
        this.titrePublicite = titrePublicite;
    }

    public String getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    public String getFilmTitre() {
        return filmTitre;
    }

    public void setFilmTitre(String filmTitre) {
        this.filmTitre = filmTitre;
    }

    public String getSalleName() {
        return salleName;
    }

    public void setSalleName(String salleName) {
        this.salleName = salleName;
    }

    public LocalDate getDateSeance() {
        return dateSeance;
    }

    public void setDateSeance(LocalDate dateSeance) {
        this.dateSeance = dateSeance;
    }
}
