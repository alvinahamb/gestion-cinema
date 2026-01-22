package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaiementSocieteDTO {
    private Integer id;
    private Integer idDiffusion;
    private String diffusionInfo;
    private BigDecimal montant;
    private LocalDate datePaiement;

    public PaiementSocieteDTO() {}

    public PaiementSocieteDTO(Integer id, Integer idDiffusion, String diffusionInfo, 
                               BigDecimal montant, LocalDate datePaiement) {
        this.id = id;
        this.idDiffusion = idDiffusion;
        this.diffusionInfo = diffusionInfo;
        this.montant = montant;
        this.datePaiement = datePaiement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdDiffusion() {
        return idDiffusion;
    }

    public void setIdDiffusion(Integer idDiffusion) {
        this.idDiffusion = idDiffusion;
    }

    public String getDiffusionInfo() {
        return diffusionInfo;
    }

    public void setDiffusionInfo(String diffusionInfo) {
        this.diffusionInfo = diffusionInfo;
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
