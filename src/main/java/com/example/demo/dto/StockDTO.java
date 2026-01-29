package com.example.demo.dto;

public class StockDTO {
    private Integer id;
    private ProduitDTO produit;
    private Integer quantiteActuelle;

    public StockDTO() {}

    public StockDTO(Integer id, ProduitDTO produit, Integer quantiteActuelle) {
        this.id = id;
        this.produit = produit;
        this.quantiteActuelle = quantiteActuelle;
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

    public Integer getQuantiteActuelle() {
        return quantiteActuelle;
    }

    public void setQuantiteActuelle(Integer quantiteActuelle) {
        this.quantiteActuelle = quantiteActuelle;
    }
}
