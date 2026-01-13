package com.example.demo.dto;

import java.time.LocalDate;
import java.math.BigDecimal;

public class TicketDTO {
    private Integer idTicket;
    private Integer idReservation;
    private Integer idClient;
    private Integer idSeance;
    private LocalDate dateAchat;
    private BigDecimal total;
    private BigDecimal remise;

    public TicketDTO() {}

    public TicketDTO(Integer idTicket, Integer idReservation, Integer idClient, Integer idSeance,
                     LocalDate dateAchat, BigDecimal total, BigDecimal remise) {
        this.idTicket = idTicket;
        this.idReservation = idReservation;
        this.idClient = idClient;
        this.idSeance = idSeance;
        this.dateAchat = dateAchat;
        this.total = total;
        this.remise = remise;
    }

    public Integer getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public Integer getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Integer idReservation) {
        this.idReservation = idReservation;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Integer getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(Integer idSeance) {
        this.idSeance = idSeance;
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

    public BigDecimal getRemise() {
        return remise;
    }

    public void setRemise(BigDecimal remise) {
        this.remise = remise;
    }
}
