package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FactureSocieteDTO {
    private Integer id;
    private Integer idSociete;
    private String nomSociete;
    private BigDecimal total;
    private BigDecimal pourcentageRestante;
    private BigDecimal montantPaye;
    private BigDecimal montantRestant;
    private LocalDate dateFacture;
    private LocalDate datePaiement;
    private LocalDate dateAjout;
    private List<FactureSocieteDetailDTO> details;

    public FactureSocieteDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getPourcentageRestante() {
        return pourcentageRestante;
    }

    public void setPourcentageRestante(BigDecimal pourcentageRestante) {
        this.pourcentageRestante = pourcentageRestante;
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

    public LocalDate getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(LocalDate dateFacture) {
        this.dateFacture = dateFacture;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public List<FactureSocieteDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<FactureSocieteDetailDTO> details) {
        this.details = details;
    }
}
