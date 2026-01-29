package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_produit")
    private Produit produit;
    
    @Column(name = "quantite_actuelle")
    private Integer quantiteActuelle;

    public Stock() {}

    public Stock(Produit produit, Integer quantiteActuelle) {
        this.produit = produit;
        this.quantiteActuelle = quantiteActuelle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Integer getQuantiteActuelle() {
        return quantiteActuelle;
    }

    public void setQuantiteActuelle(Integer quantiteActuelle) {
        this.quantiteActuelle = quantiteActuelle;
    }
}
