package com.example.demo.dto;

import java.math.BigDecimal;

public class SocieteResumeDTO {
    private Integer idSociete;
    private String nomSociete;
    private BigDecimal montantTotal;
    private BigDecimal montantPaye;
    private BigDecimal montantRestant;
    private Integer nombreDiffusions;

    public SocieteResumeDTO() {}

    public SocieteResumeDTO(Integer idSociete, String nomSociete, BigDecimal montantTotal, 
                            BigDecimal montantPaye, BigDecimal montantRestant, Integer nombreDiffusions) {
        this.idSociete = idSociete;
        this.nomSociete = nomSociete;
        this.montantTotal = montantTotal;
        this.montantPaye = montantPaye;
        this.montantRestant = montantRestant;
        this.nombreDiffusions = nombreDiffusions;
    }

    public Integer getIdSociete() {
        return idSociete;
    }

    public void setIdSociete(Integer idSociete) {
        this.idSociete = idSociete;
    }

    public String getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
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

    public Integer getNombreDiffusions() {
        return nombreDiffusions;
    }

    public void setNombreDiffusions(Integer nombreDiffusions) {
        this.nombreDiffusions = nombreDiffusions;
    }
}
