package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "methode_paiement")
public class MethodePaiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMethodePaiement;
    
    @Column(nullable = false)
    private String libelle;

    public MethodePaiement() {}

    public MethodePaiement(String libelle) {
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
