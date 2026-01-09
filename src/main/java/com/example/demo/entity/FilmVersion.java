package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "film_version")
public class FilmVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_film")
    private Film film;
    
    @ManyToOne
    @JoinColumn(name = "id_langue")
    private Langue langue;
    
    private Boolean sousTitre;

    public FilmVersion() {}

    public FilmVersion(Film film, Langue langue, Boolean sousTitre) {
        this.film = film;
        this.langue = langue;
        this.sousTitre = sousTitre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Langue getLangue() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    public Boolean getSousTitre() {
        return sousTitre;
    }

    public void setSousTitre(Boolean sousTitre) {
        this.sousTitre = sousTitre;
    }
}
