package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "paiement_societe")
public class PaiementSociete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_diffusion")
    private Diffusion diffusion;
    
    @Column(precision = 12, scale = 2)
    private BigDecimal montant;
    
    private LocalDate datePaiement;

    public PaiementSociete() {}

    public PaiementSociete(Diffusion diffusion, BigDecimal montant, LocalDate datePaiement) {
        this.diffusion = diffusion;
        this.montant = montant;
        this.datePaiement = datePaiement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Diffusion getDiffusion() {
        return diffusion;
    }

    public void setDiffusion(Diffusion diffusion) {
        this.diffusion = diffusion;
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
