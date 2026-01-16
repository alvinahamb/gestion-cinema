package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTicket;
    
    @ManyToOne
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;
    
    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;
    
    @ManyToOne
    @JoinColumn(name = "id_seance")
    private Seance seance;
    
    private LocalDate dateAchat;
    
    private BigDecimal total;

    public Ticket() {}

    public Ticket(Reservation reservation, Client client, Seance seance, LocalDate dateAchat, BigDecimal total) {
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

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
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
}
