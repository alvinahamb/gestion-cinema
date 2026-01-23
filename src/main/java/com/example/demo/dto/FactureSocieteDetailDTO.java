package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FactureSocieteDetailDTO {
    private Integer id;
    private Integer idFactureSociete;
    private BigDecimal montant;
    private BigDecimal pourcentagePaye;
    private LocalDate datePaiement;

    public FactureSocieteDetailDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdFactureSociete() {
        return idFactureSociete;
    }

    public void setIdFactureSociete(Integer idFactureSociete) {
        this.idFactureSociete = idFactureSociete;
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
