package com.example.demo.dto;

public class GenreDTO {
    private Integer idGenre;
    private String libelle;

    public GenreDTO() {}

    public GenreDTO(Integer idGenre, String libelle) {
        this.idGenre = idGenre;
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
}
