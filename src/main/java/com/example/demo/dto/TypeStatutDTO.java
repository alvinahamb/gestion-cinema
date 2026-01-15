package com.example.demo.dto;

public class TypeStatutDTO {
    private Integer idTypeStatut;
    private String libelle;

    public TypeStatutDTO() {}

    public TypeStatutDTO(Integer idTypeStatut, String libelle) {
        this.idTypeStatut = idTypeStatut;
        this.libelle = libelle;
    }

    public Integer getIdTypeStatut() {
        return idTypeStatut;
    }

    public void setIdTypeStatut(Integer idTypeStatut) {
        this.idTypeStatut = idTypeStatut;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}