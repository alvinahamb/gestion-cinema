package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReservation;
    
    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;
    
    @ManyToOne
    @JoinColumn(name = "id_seance")
    private Seance seance;
    
    private LocalDateTime dateReservation;
    
    private BigDecimal total;
    
    private BigDecimal remise;
    
    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;
    
    private LocalDate datePaiementPrevu;

    public Reservation() {}

    public Reservation(Client client, Seance seance, LocalDateTime dateReservation, BigDecimal total,
                      BigDecimal remise, Status status, LocalDate datePaiementPrevu) {
        this.client = client;
        this.seance = seance;
        this.dateReservation = dateReservation;
        this.total = total;
        this.remise = remise;
        this.status = status;
        this.datePaiementPrevu = datePaiementPrevu;
    }

    public Integer getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Integer idReservation) {
        this.idReservation = idReservation;
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

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
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

