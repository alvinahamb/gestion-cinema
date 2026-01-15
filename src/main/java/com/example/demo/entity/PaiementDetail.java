package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "paiement_detail")
public class PaiementDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_paiement")
    private Paiement paiement;
    
    @ManyToOne
    @JoinColumn(name = "id_methode_paiement")
    private MethodePaiement methodePaiement;
    
    private BigDecimal montant;
    
    private LocalDate datePaiement;

    public PaiementDetail() {}

    public PaiementDetail(Paiement paiement, MethodePaiement methodePaiement, 
                         BigDecimal montant, LocalDate datePaiement) {
        this.paiement = paiement;
        this.methodePaiement = methodePaiement;
        this.montant = montant;
        this.datePaiement = datePaiement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    public MethodePaiement getMethodePaiement() {
        return methodePaiement;
    }

    public void setMethodePaiement(MethodePaiement methodePaiement) {
        this.methodePaiement = methodePaiement;
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
