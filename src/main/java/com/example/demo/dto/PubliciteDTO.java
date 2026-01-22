package com.example.demo.dto;

import java.time.LocalDate;

public class PubliciteDTO {
    private Integer id;
    private String titre;
    private Integer duree;
    private LocalDate dateAjout;
    private String code;

    public PubliciteDTO() {}

    public PubliciteDTO(Integer id, String titre, Integer duree, LocalDate dateAjout, String code) {
        this.id = id;
        this.titre = titre;
        this.duree = duree;
        this.dateAjout = dateAjout;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
