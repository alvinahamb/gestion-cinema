package com.example.demo.dto;

public class FilmVersionDTO {
    private Integer id;
    private Integer idFilm;
    private FilmDTO film;
    private Integer idLangue;
    private LangueDTO langue;
    private Boolean sousTitre;

    public FilmVersionDTO() {}

    public FilmVersionDTO(Integer id, FilmDTO film, LangueDTO langue, Boolean sousTitre) {
        this.id = id;
        this.film = film;
        this.langue = langue;
        this.sousTitre = sousTitre;
        this.idFilm = film != null ? film.getIdFilm() : null;
        this.idLangue = langue != null ? langue.getIdLangue() : null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(Integer idFilm) {
        this.idFilm = idFilm;
    }

    public FilmDTO getFilm() {
        return film;
    }

    public void setFilm(FilmDTO film) {
        this.film = film;
        this.idFilm = film != null ? film.getIdFilm() : null;
    }

    public Integer getIdLangue() {
        return idLangue;
    }

    public void setIdLangue(Integer idLangue) {
        this.idLangue = idLangue;
    }

    public LangueDTO getLangue() {
        return langue;
    }

    public void setLangue(LangueDTO langue) {
        this.langue = langue;
        this.idLangue = langue != null ? langue.getIdLangue() : null;
    }

    public Boolean getSousTitre() {
        return sousTitre;
    }

    public void setSousTitre(Boolean sousTitre) {
        this.sousTitre = sousTitre;
    }
}