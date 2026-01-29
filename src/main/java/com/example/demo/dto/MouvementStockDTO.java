package com.example.demo.dto;

import java.time.LocalDate;

public class MouvementStockDTO {
    private Integer id;
    private ProduitDTO produit;
    private Integer produitId;
    private Integer entree;
    private Integer sortie;
    private Integer quantiteActuelle;
    private LocalDate dateMouvement;
    private VenteProduitDetailDTO venteProduitDetail;
    private Integer venteProduitDetailId;

    public MouvementStockDTO() {}

    public MouvementStockDTO(Integer id, ProduitDTO produit, Integer entree, Integer sortie, Integer quantiteActuelle, LocalDate dateMouvement, VenteProduitDetailDTO venteProduitDetail) {
        this.id = id;
        this.produit = produit;
        this.entree = entree;
        this.sortie = sortie;
        this.quantiteActuelle = quantiteActuelle;
        this.dateMouvement = dateMouvement;
        this.venteProduitDetail = venteProduitDetail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getEntree() {
        return entree;
    }

    public void setEntree(Integer entree) {
        this.entree = entree;
    }

    public Integer getSortie() {
        return sortie;
    }

    public void setSortie(Integer sortie) {
        this.sortie = sortie;
    }

    public Integer getQuantiteActuelle() {
        return quantiteActuelle;
    }

    public void setQuantiteActuelle(Integer quantiteActuelle) {
        this.quantiteActuelle = quantiteActuelle;
    }

    public LocalDate getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(LocalDate dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    public VenteProduitDetailDTO getVenteProduitDetail() {
        return venteProduitDetail;
    }

    public void setVenteProduitDetail(VenteProduitDetailDTO venteProduitDetail) {
        this.venteProduitDetail = venteProduitDetail;
    }

    public Integer getVenteProduitDetailId() {
        return venteProduitDetailId;
    }

    public void setVenteProduitDetailId(Integer venteProduitDetailId) {
        this.venteProduitDetailId = venteProduitDetailId;
    }
}
