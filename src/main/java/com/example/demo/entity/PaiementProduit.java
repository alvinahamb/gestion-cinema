package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "paiements_produits")
public class PaiementProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_vente")
    private VenteProduit venteProduit;
    
    @Column(name = "montant_paye")
    private BigDecimal montantPaye;
    
    @Column(name = "montant_restant")
    private BigDecimal montantRestant;
    
    @Column(name = "date_paiement")
    private LocalDate datePaiement;

    public PaiementProduit() {}

    public PaiementProduit(VenteProduit venteProduit, BigDecimal montantPaye, BigDecimal montantRestant, LocalDate datePaiement) {
        this.venteProduit = venteProduit;
        this.montantPaye = montantPaye;
        this.montantRestant = montantRestant;
        this.datePaiement = datePaiement;
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

    public BigDecimal getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(BigDecimal montantPaye) {
        this.montantPaye = montantPaye;
    }

    public BigDecimal getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(BigDecimal montantRestant) {
        this.montantRestant = montantRestant;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }
}
