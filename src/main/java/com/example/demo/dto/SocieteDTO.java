package com.example.demo.dto;

import java.time.LocalDate;

public class SocieteDTO {
    private Integer id;
    private String nom;
    private LocalDate dateAjout;
    private String telephone;
    private String adresse;

    public SocieteDTO() {}

    public SocieteDTO(Integer id, String nom, LocalDate dateAjout, String telephone, String adresse) {
        this.id = id;
        this.nom = nom;
        this.dateAjout = dateAjout;
        this.telephone = telephone;
        this.adresse = adresse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
