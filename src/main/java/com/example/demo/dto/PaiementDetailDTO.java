package com.example.demo.dto;

import java.time.LocalDate;
import java.math.BigDecimal;

public class PaiementDetailDTO {
    private Integer id;
    private Integer idPaiement;
    private PaiementDTO paiement;
    private Integer idMethodePaiement;
    private MethodePaiementDTO methodePaiement;
    private BigDecimal montant;
    private LocalDate datePaiement;

    public PaiementDetailDTO() {}

    public PaiementDetailDTO(Integer id, PaiementDTO paiement, MethodePaiementDTO methodePaiement, 
                            BigDecimal montant, LocalDate datePaiement) {
        this.id = id;
        this.paiement = paiement;
        this.methodePaiement = methodePaiement;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.idPaiement = paiement != null ? paiement.getIdPaiement() : null;
        this.idMethodePaiement = methodePaiement != null ? methodePaiement.getIdMethodePaiement() : null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(Integer idPaiement) {
        this.idPaiement = idPaiement;
    }

    public PaiementDTO getPaiement() {
        return paiement;
    }

    public void setPaiement(PaiementDTO paiement) {
        this.paiement = paiement;
        this.idPaiement = paiement != null ? paiement.getIdPaiement() : null;
    }

    public Integer getIdMethodePaiement() {
        return idMethodePaiement;
    }

    public void setIdMethodePaiement(Integer idMethodePaiement) {
        this.idMethodePaiement = idMethodePaiement;
    }

    public MethodePaiementDTO getMethodePaiement() {
        return methodePaiement;
    }

    public void setMethodePaiement(MethodePaiementDTO methodePaiement) {
        this.methodePaiement = methodePaiement;
        this.idMethodePaiement = methodePaiement != null ? methodePaiement.getIdMethodePaiement() : null;
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