package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PaiementProduitDTO {
    private Integer id;
    private VenteProduitDTO venteProduit;
    private Integer venteProduitId;
    private BigDecimal montantPaye;
    private BigDecimal montantRestant;
    private LocalDate datePaiement;
    private List<PaiementProduitDetailDTO> details;

    public PaiementProduitDTO() {}

    public PaiementProduitDTO(Integer id, VenteProduitDTO venteProduit, BigDecimal montantPaye, BigDecimal montantRestant, LocalDate datePaiement) {
        this.id = id;
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

    public VenteProduitDTO getVenteProduit() {
        return venteProduit;
    }

    public void setVenteProduit(VenteProduitDTO venteProduit) {
        this.venteProduit = venteProduit;
    }

    public Integer getVenteProduitId() {
        return venteProduitId;
    }

    public void setVenteProduitId(Integer venteProduitId) {
        this.venteProduitId = venteProduitId;
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

    public List<PaiementProduitDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<PaiementProduitDetailDTO> details) {
        this.details = details;
    }
}
