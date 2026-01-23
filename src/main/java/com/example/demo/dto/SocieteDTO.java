package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SocieteDTO {
    private Integer id;
    private String nom;
    private LocalDate dateAjout;
    private String telephone;
    private String adresse;
    private List<FactureSocieteDTO> factures;
    private BigDecimal totalFactures;
    private BigDecimal totalPaye;
    private BigDecimal totalRestant;

    public SocieteDTO() {}

    public SocieteDTO(Integer id, String nom, LocalDate dateAjout, String telephone, String adresse) {
        this.id = id;
        this.nom = nom;
        this.dateAjout = dateAjout;
        this.telephone = telephone;
        this.adresse = adresse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<FactureSocieteDTO> getFactures() {
        return factures;
    }

    public void setFactures(List<FactureSocieteDTO> factures) {
        this.factures = factures;
    }

    public BigDecimal getTotalFactures() {
        return totalFactures;
    }

    public void setTotalFactures(BigDecimal totalFactures) {
        this.totalFactures = totalFactures;
    }

    public BigDecimal getTotalPaye() {
        return totalPaye;
    }

    public void setTotalPaye(BigDecimal totalPaye) {
        this.totalPaye = totalPaye;
    }

    public BigDecimal getTotalRestant() {
        return totalRestant;
    }

    public void setTotalRestant(BigDecimal totalRestant) {
        this.totalRestant = totalRestant;
    }
}
