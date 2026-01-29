package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "paiements_produit_details")
public class PaiementProduitDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_paiements")
    private PaiementProduit paiementProduit;
    
    private BigDecimal montant;
    
    @Column(name = "date_paiement")
    private LocalDate datePaiement;

    public PaiementProduitDetail() {}

    public PaiementProduitDetail(PaiementProduit paiementProduit, BigDecimal montant, LocalDate datePaiement) {
        this.paiementProduit = paiementProduit;
        this.montant = montant;
        this.datePaiement = datePaiement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PaiementProduit getPaiementProduit() {
        return paiementProduit;
    }

    public void setPaiementProduit(PaiementProduit paiementProduit) {
        this.paiementProduit = paiementProduit;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }
}
