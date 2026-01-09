package com.example.demo.dto;

import java.time.LocalDate;

public class FilmDTO {
    private Integer idFilm;
    private String titre;
    private String synopsis;
    private Integer duree;
    private Integer idLangue;
    private Integer idPays;
    private LocalDate dateSortie;
    private LocalDate dateAjout;
    private Integer classificationAge;
    private Integer idStatus;

    public FilmDTO() {}

    public FilmDTO(Integer idFilm, String titre, String synopsis, Integer duree, Integer idLangue, 
                   Integer idPays, LocalDate dateSortie, LocalDate dateAjout, Integer classificationAge, Integer idStatus) {
        this.idFilm = idFilm;
        this.titre = titre;
        this.synopsis = synopsis;
        this.duree = duree;
        this.idLangue = idLangue;
        this.idPays = idPays;
        this.dateSortie = dateSortie;
        this.dateAjout = dateAjout;
        this.classificationAge = classificationAge;
        this.idStatus = idStatus;
    }

    public Integer getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(Integer idFilm) {
        this.idFilm = idFilm;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Integer getIdLangue() {
        return idLangue;
    }

    public void setIdLangue(Integer idLangue) {
        this.idLangue = idLangue;
    }

    public Integer getIdPays() {
        return idPays;
    }

    public void setIdPays(Integer idPays) {
        this.idPays = idPays;
    }

    public LocalDate getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Integer getClassificationAge() {
        return classificationAge;
    }

    public void setClassificationAge(Integer classificationAge) {
        this.classificationAge = classificationAge;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }
}
