package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "mouvement_total")
public class MouvementStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_produit")
    private Produit produit;
    
    private Integer entree;
    
    private Integer sortie;
    
    @Column(name = "quatite_actuelle")
    private Integer quantiteActuelle;
    
    @Column(name = "date_mouvement")
    private LocalDate dateMouvement;
    
    @ManyToOne
    @JoinColumn(name = "id_vente_produit_detail")
    private VenteProduitDetail venteProduitDetail;

    public MouvementStock() {}

    public MouvementStock(Produit produit, Integer entree, Integer sortie, Integer quantiteActuelle, LocalDate dateMouvement, VenteProduitDetail venteProduitDetail) {
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

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
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

    public VenteProduitDetail getVenteProduitDetail() {
        return venteProduitDetail;
    }

    public void setVenteProduitDetail(VenteProduitDetail venteProduitDetail) {
        this.venteProduitDetail = venteProduitDetail;
    }
}
