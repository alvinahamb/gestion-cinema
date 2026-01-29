package com.example.demo.dto;

import java.math.BigDecimal;

public class VenteProduitDetailDTO {
    private Integer id;
    private Integer venteProduitId;
    private ProduitDTO produit;
    private Integer produitId;
    private Integer quantite;
    private BigDecimal prixUnitaire;
    private BigDecimal sousTotal;

    public VenteProduitDetailDTO() {}

    public VenteProduitDetailDTO(Integer id, Integer venteProduitId, ProduitDTO produit, Integer quantite, BigDecimal prixUnitaire) {
        this.id = id;
        this.venteProduitId = venteProduitId;
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        if (prixUnitaire != null && quantite != null) {
            this.sousTotal = prixUnitaire.multiply(BigDecimal.valueOf(quantite));
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVenteProduitId() {
        return venteProduitId;
    }

    public void setVenteProduitId(Integer venteProduitId) {
        this.venteProduitId = venteProduitId;
    }

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }

    public Integer getProduitId() {
        return produitId;
    }

    public void setProduitId(Integer produitId) {
        this.produitId = produitId;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getSousTotal() {
        return sousTotal;
    }

    public void setSousTotal(BigDecimal sousTotal) {
        this.sousTotal = sousTotal;
    }
}
