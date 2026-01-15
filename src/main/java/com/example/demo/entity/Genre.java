package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGenre;
    
    @Column(nullable = false)
    private String libelle;
    
    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilmGenre> filmGenres;

    public Genre() {}

    public Genre(String libelle) {
        this.libelle = libelle;
    }

    public Integer getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(Integer idGenre) {
        this.idGenre = idGenre;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<FilmGenre> getFilmGenres() {
        return filmGenres;
    }

    public void setFilmGenres(List<FilmGenre> filmGenres) {
        this.filmGenres = filmGenres;
    }
}
