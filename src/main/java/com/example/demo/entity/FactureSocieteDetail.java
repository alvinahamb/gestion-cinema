package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "facture_societe_detail")
public class FactureSocieteDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_facture_societe")
    private FactureSociete factureSociete;
    
    @Column(precision = 12, scale = 2)
    private BigDecimal montant;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal pourcentagePaye;
    
    private LocalDate datePaiement;

    public FactureSocieteDetail() {}

    public FactureSocieteDetail(FactureSociete factureSociete, BigDecimal montant, 
                                 BigDecimal pourcentagePaye, LocalDate datePaiement) {
        this.factureSociete = factureSociete;
        this.montant = montant;
        this.pourcentagePaye = pourcentagePaye;
        this.datePaiement = datePaiement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FactureSociete getFactureSociete() {
        return factureSociete;
    }

    public void setFactureSociete(FactureSociete factureSociete) {
        this.factureSociete = factureSociete;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public BigDecimal getPourcentagePaye() {
        return pourcentagePaye;
    }

    public void setPourcentagePaye(BigDecimal pourcentagePaye) {
        this.pourcentagePaye = pourcentagePaye;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }
}
