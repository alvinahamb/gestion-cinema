package com.example.demo.dto;

public class FilmGenreDTO {
    private Integer id;
    private FilmDTO film;
    private GenreDTO genre;

    public FilmGenreDTO() {}

    public FilmGenreDTO(Integer id, FilmDTO film, GenreDTO genre) {
        this.id = id;
        this.film = film;
        this.genre = genre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FilmDTO getFilm() {
        return film;
    }

    public Integer getIdFilm() {
        return film != null ? film.getIdFilm() : null;
    }

    public void setFilm(FilmDTO film) {
        this.film = film;
    }

    public GenreDTO getGenre() {
        return genre;
    }

    public Integer getIdGenre() {
        return genre != null ? genre.getIdGenre() : null;
    }

    public void setGenre(GenreDTO genre) {
        this.genre = genre;
    }
}