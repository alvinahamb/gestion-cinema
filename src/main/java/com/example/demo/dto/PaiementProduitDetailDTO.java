package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaiementProduitDetailDTO {
    private Integer id;
    private Integer paiementProduitId;
    private BigDecimal montant;
    private LocalDate datePaiement;

    public PaiementProduitDetailDTO() {}

    public PaiementProduitDetailDTO(Integer id, Integer paiementProduitId, BigDecimal montant, LocalDate datePaiement) {
        this.id = id;
        this.paiementProduitId = paiementProduitId;
        this.montant = montant;
        this.datePaiement = datePaiement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaiementProduitId() {
        return paiementProduitId;
    }

    public void setPaiementProduitId(Integer paiementProduitId) {
        this.paiementProduitId = paiementProduitId;
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
