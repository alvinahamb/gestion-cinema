package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public class ReservationDTO {
    private Integer idReservation;
    private ClientDTO client;
    private SeanceDTO seance;
    private LocalDateTime dateReservation;
    private BigDecimal total;
    private BigDecimal remise;
    private StatusDTO status;
    private LocalDate datePaiementPrevu;

    public ReservationDTO() {}

    public ReservationDTO(Integer idReservation, ClientDTO client, SeanceDTO seance, 
                         LocalDateTime dateReservation, BigDecimal total, BigDecimal remise,
                         StatusDTO status, LocalDate datePaiementPrevu) {
        this.idReservation = idReservation;
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
