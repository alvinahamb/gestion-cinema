package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "paiement")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPaiement;
    
    @ManyToOne
    @JoinColumn(name = "id_ticket")
    private Ticket ticket;
    
    private BigDecimal montant;
    
    private LocalDate creerLe;
    
    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;
    
    private LocalDate datePaiementPrevu;

    public Paiement() {}

    public Paiement(Ticket ticket, BigDecimal montant, LocalDate creerLe, 
                   Status status, LocalDate datePaiementPrevu) {
        this.ticket = ticket;
        this.montant = montant;
        this.creerLe = creerLe;
        this.status = status;
        this.datePaiementPrevu = datePaiementPrevu;
    }

    public Integer getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(Integer idPaiement) {
        this.idPaiement = idPaiement;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDate getCreerLe() {
        return creerLe;
    }

    public void setCreerLe(LocalDate creerLe) {
        this.creerLe = creerLe;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDatePaiementPrevu() {
        return datePaiementPrevu;
    }

    public void setDatePaiementPrevu(LocalDate datePaiementPrevu) {
        this.datePaiementPrevu = datePaiementPrevu;
    }
}
