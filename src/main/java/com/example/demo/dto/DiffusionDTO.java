package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DiffusionDTO {
    private Integer id;
    private Integer idSociete;
    private String nomSociete;
    private Integer idPublicite;
    private String titrePublicite;
    private Integer idTarif;
    private String tarifLibelle;
    private Integer mois;
    private Integer annee;
    private LocalDate dateDiffusion;
    private Integer nombre;
    private Integer diffAttribue;
    private Integer diffRestant;
    private LocalDate dateAjout;
    private BigDecimal montantRestant;
    private BigDecimal montantTotal;

    public DiffusionDTO() {}

    public DiffusionDTO(Integer id, Integer idSociete, String nomSociete, Integer idPublicite, 
                        String titrePublicite, Integer idTarif, String tarifLibelle, Integer mois, 
                        Integer annee, LocalDate dateDiffusion, Integer nombre, Integer diffAttribue, 
                        Integer diffRestant, LocalDate dateAjout) {
        this.id = id;
        this.idSociete = idSociete;
        this.nomSociete = nomSociete;
        this.idPublicite = idPublicite;
        this.titrePublicite = titrePublicite;
        this.idTarif = idTarif;
        this.tarifLibelle = tarifLibelle;
        this.mois = mois;
        this.annee = annee;
        this.dateDiffusion = dateDiffusion;
        this.nombre = nombre;
        this.diffAttribue = diffAttribue;
        this.diffRestant = diffRestant;
        this.dateAjout = dateAjout;
    }

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

    public Integer getIdPublicite() {
        return idPublicite;
    }

    public void setIdPublicite(Integer idPublicite) {
        this.idPublicite = idPublicite;
    }

    public String getTitrePublicite() {
        return titrePublicite;
    }

    public void setTitrePublicite(String titrePublicite) {
        this.titrePublicite = titrePublicite;
    }

    public Integer getIdTarif() {
        return idTarif;
    }

    public void setIdTarif(Integer idTarif) {
        this.idTarif = idTarif;
    }

    public String getTarifLibelle() {
        return tarifLibelle;
    }

    public void setTarifLibelle(String tarifLibelle) {
        this.tarifLibelle = tarifLibelle;
    }

    public Integer getMois() {
        return mois;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public LocalDate getDateDiffusion() {
        return dateDiffusion;
    }

    public void setDateDiffusion(LocalDate dateDiffusion) {
        this.dateDiffusion = dateDiffusion;
    }

    public Integer getNombre() {
        return nombre;
    }

    public void setNombre(Integer nombre) {
        this.nombre = nombre;
    }

    public Integer getDiffAttribue() {
        return diffAttribue;
    }

    public void setDiffAttribue(Integer diffAttribue) {
        this.diffAttribue = diffAttribue;
    }

    public Integer getDiffRestant() {
        return diffRestant;
    }

    public void setDiffRestant(Integer diffRestant) {
        this.diffRestant = diffRestant;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public BigDecimal getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(BigDecimal montantRestant) {
        this.montantRestant = montantRestant;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }
}
