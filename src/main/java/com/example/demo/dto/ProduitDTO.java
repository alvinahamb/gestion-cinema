package com.example.demo.dto;

import java.math.BigDecimal;

public class ProduitDTO {
    private Integer id;
    private String nom;
    private BigDecimal prix;

    public ProduitDTO() {}

    public ProduitDTO(Integer id, String nom, BigDecimal prix) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
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

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }
}
