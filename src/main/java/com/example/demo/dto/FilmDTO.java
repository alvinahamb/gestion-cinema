package com.example.demo.dto;

import java.time.LocalDate;

public class FilmDTO {
    private Integer idFilm;
    private String titre;
    private String synopsis;
    private Integer duree;
    private Integer idLangueOriginal;
    private Integer idPays;
    private LocalDate dateSortie;
    private LocalDate dateAjout;
    private Integer classificationAge;

    public FilmDTO() {}

    public FilmDTO(Integer idFilm, String titre, String synopsis, Integer duree, Integer idLangueOriginal, 
                   Integer idPays, LocalDate dateSortie, LocalDate dateAjout, Integer classificationAge) {
        this.idFilm = idFilm;
        this.titre = titre;
        this.synopsis = synopsis;
        this.duree = duree;
        this.idLangueOriginal = idLangueOriginal;
        this.idPays = idPays;
        this.dateSortie = dateSortie;
        this.dateAjout = dateAjout;
        this.classificationAge = classificationAge;
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

    public Integer getIdLangueOriginal() {
        return idLangueOriginal;
    }

    public void setIdLangueOriginal(Integer idLangueOriginal) {
        this.idLangueOriginal = idLangueOriginal;
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
}
