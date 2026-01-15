package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

public class FilmDTO {
    private Integer idFilm;
    private String titre;
    private String synopsis;
    private Integer duree;
    private LangueDTO langueOriginal;
    private PaysDTO pays;
    private LocalDate dateSortie;
    private LocalDate dateAjout;
    private Integer classificationAge;
    private List<FilmGenreDTO> filmGenres;
    private List<FilmVersionDTO> versions;
    private List<Integer> idGenres;

    public FilmDTO() {}

    public FilmDTO(Integer idFilm, String titre, String synopsis, Integer duree, LangueDTO langueOriginal, 
                   PaysDTO pays, LocalDate dateSortie, LocalDate dateAjout, Integer classificationAge) {
        this.idFilm = idFilm;
        this.titre = titre;
        this.synopsis = synopsis;
        this.duree = duree;
        this.langueOriginal = langueOriginal;
        this.pays = pays;
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

    public LangueDTO getLangueOriginal() {
        return langueOriginal;
    }

    public void setLangueOriginal(LangueDTO langueOriginal) {
        this.langueOriginal = langueOriginal;
    }

    public PaysDTO getPays() {
        return pays;
    }

    public void setPays(PaysDTO pays) {
        this.pays = pays;
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

    public List<Integer> getIdGenres() {
        return idGenres;
    }

    public void setIdGenres(List<Integer> idGenres) {
        this.idGenres = idGenres;
    }

    public List<FilmGenreDTO> getFilmGenres() {
        return filmGenres;
    }

    public void setFilmGenres(List<FilmGenreDTO> filmGenres) {
        this.filmGenres = filmGenres;
    }

    public List<FilmVersionDTO> getVersions() {
        return versions;
    }

    public void setVersions(List<FilmVersionDTO> versions) {
        this.versions = versions;
    }
}
