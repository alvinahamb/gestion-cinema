package com.example.demo.dto;

public class LangueDTO {
    private Integer idLangue;
    private String nom;
    private String code;

    public LangueDTO() {}

    public LangueDTO(Integer idLangue, String nom, String code) {
        this.idLangue = idLangue;
        this.nom = nom;
        this.code = code;
    }

    public Integer getIdLangue() {
        return idLangue;
    }

    public void setIdLangue(Integer idLangue) {
        this.idLangue = idLangue;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
