package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vente_produit_detail")
public class VenteProduitDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_vente")
    private VenteProduit venteProduit;
    
    @ManyToOne
    @JoinColumn(name = "id_produit")
    private Produit produit;
    
    private Integer quantite;
    
    @Column(name = "prix_unitaire")
    private BigDecimal prixUnitaire;

    public VenteProduitDetail() {}

    public VenteProduitDetail(VenteProduit venteProduit, Produit produit, Integer quantite, BigDecimal prixUnitaire) {
        this.venteProduit = venteProduit;
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VenteProduit getVenteProduit() {
        return venteProduit;
    }

    public void setVenteProduit(VenteProduit venteProduit) {
        this.venteProduit = venteProduit;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
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
        if (prixUnitaire != null && quantite != null) {
            return prixUnitaire.multiply(BigDecimal.valueOf(quantite));
        }
        return BigDecimal.ZERO;
    }
}
