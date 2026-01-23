package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "facture_societe")
public class FactureSociete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_societe")
    private Societe societe;
    
    @Column(precision = 12, scale = 2)
    private BigDecimal total;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal pourcentageRestante;
    
    private LocalDate dateFacture;
    
    private LocalDate datePaiement;
    
    private LocalDate dateAjout;

    public FactureSociete() {}

    public FactureSociete(Societe societe, BigDecimal total, 
                          BigDecimal pourcentageRestante, LocalDate dateFacture, 
                          LocalDate datePaiement, LocalDate dateAjout) {
        this.societe = societe;
        this.total = total;
        this.pourcentageRestante = pourcentageRestante;
        this.dateFacture = dateFacture;
        this.datePaiement = datePaiement;
        this.dateAjout = dateAjout;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Societe getSociete() {
        return societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
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
}
