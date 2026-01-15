package com.example.demo.dto;

import java.time.LocalDate;
import java.math.BigDecimal;

public class PaiementDTO {
    private Integer idPaiement;
    private TicketDTO ticket;
    private BigDecimal montant;
    private LocalDate creerLe;
    private StatusDTO status;
    private LocalDate datePaiementPrevu;

    public PaiementDTO() {}

    public PaiementDTO(Integer idPaiement, TicketDTO ticket, BigDecimal montant, 
                      LocalDate creerLe, StatusDTO status, LocalDate datePaiementPrevu) {
        this.idPaiement = idPaiement;
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

    public TicketDTO getTicket() {
        return ticket;
    }

    public void setTicket(TicketDTO ticket) {
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

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public LocalDate getDatePaiementPrevu() {
        return datePaiementPrevu;
    }

    public void setDatePaiementPrevu(LocalDate datePaiementPrevu) {
        this.datePaiementPrevu = datePaiementPrevu;
    }
}
