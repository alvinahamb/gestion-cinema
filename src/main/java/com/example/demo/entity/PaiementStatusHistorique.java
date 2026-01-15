package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "paiement_status_historique")
public class PaiementStatusHistorique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_paiement")
    private Paiement paiement;
    
    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;
    
    private LocalDate dateModification;

    public PaiementStatusHistorique() {}

    public PaiementStatusHistorique(Paiement paiement, Status status, LocalDate dateModification) {
        this.paiement = paiement;
        this.status = status;
        this.dateModification = dateModification;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }
}
