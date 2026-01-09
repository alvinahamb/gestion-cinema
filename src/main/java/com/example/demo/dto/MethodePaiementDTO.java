package com.example.demo.dto;

public class MethodePaiementDTO {
    private Integer idMethodePaiement;
    private String libelle;

    public MethodePaiementDTO() {}

    public MethodePaiementDTO(Integer idMethodePaiement, String libelle) {
        this.idMethodePaiement = idMethodePaiement;
        this.libelle = libelle;
    }

    public Integer getIdMethodePaiement() {
        return idMethodePaiement;
    }

    public void setIdMethodePaiement(Integer idMethodePaiement) {
        this.idMethodePaiement = idMethodePaiement;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
