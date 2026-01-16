package com.example.demo.dto;

import java.time.LocalDate;
import java.math.BigDecimal;

public class TicketDTO {
    private Integer idTicket;
    private ReservationDTO reservation;
    private ClientDTO client;
    private SeanceDTO seance;
    private LocalDate dateAchat;
    private BigDecimal total;
    private BigDecimal montantRestant;

    public TicketDTO() {}

    public TicketDTO(Integer idTicket, ReservationDTO reservation, ClientDTO client, SeanceDTO seance,
                     LocalDate dateAchat, BigDecimal total) {
        this.idTicket = idTicket;
        this.reservation = reservation;
        this.client = client;
        this.seance = seance;
        this.dateAchat = dateAchat;
        this.total = total;
    }

    public Integer getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public ReservationDTO getReservation() {
        return reservation;
    }

    public void setReservation(ReservationDTO reservation) {
        this.reservation = reservation;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public SeanceDTO getSeance() {
        return seance;
    }

    public void setSeance(SeanceDTO seance) {
        this.seance = seance;
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(BigDecimal montantRestant) {
        this.montantRestant = montantRestant;
    }
}
