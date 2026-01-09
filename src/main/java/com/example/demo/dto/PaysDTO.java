package com.example.demo.dto;

public class PaysDTO {
    private Integer idPays;
    private String nom;

    public PaysDTO() {}

    public PaysDTO(Integer idPays, String nom) {
        this.idPays = idPays;
        this.nom = nom;
    }

    public Integer getIdPays() {
        return idPays;
    }

    public void setIdPays(Integer idPays) {
        this.idPays = idPays;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
