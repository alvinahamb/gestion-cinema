package com.example.demo.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class ReservationDTO {
    private Integer idReservation;
    private ClientDTO client;
    private Integer idSeance;
    private LocalDateTime dateReservation;
    private BigDecimal total;
    private BigDecimal remise;
    private Integer idStatus;
    private String statusLibelle;

    public ReservationDTO() {}

    public ReservationDTO(Integer idReservation, ClientDTO client, Integer idSeance, 
                         LocalDateTime dateReservation, BigDecimal total, BigDecimal remise,
                         Integer idStatus, String statusLibelle) {
        this.idReservation = idReservation;
        this.client = client;
        this.idSeance = idSeance;
        this.dateReservation = dateReservation;
        this.total = total;
        this.remise = remise;
        this.idStatus = idStatus;
        this.statusLibelle = statusLibelle;
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

    public Integer getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(Integer idSeance) {
        this.idSeance = idSeance;
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

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public String getStatusLibelle() {
        return statusLibelle;
    }

    public void setStatusLibelle(String statusLibelle) {
        this.statusLibelle = statusLibelle;
    }
}
