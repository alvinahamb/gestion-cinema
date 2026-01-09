package com.example.demo.dto;

import java.time.LocalDate;
import java.math.BigDecimal;

public class ReservationDTO {
    private Integer idReservation;
    private ClientDTO client;
    private Integer idSeance;
    private LocalDate dateReservation;
    private BigDecimal total;
    private LocalDate datePaiementPrevu;
    private LocalDate datePaiementEffectue;
    private Integer idMethodePaiement;
    private Integer idStatus;
    private String statusLibelle;

    public ReservationDTO() {}

    public ReservationDTO(Integer idReservation, ClientDTO client, Integer idSeance, 
                         LocalDate dateReservation, BigDecimal total, LocalDate datePaiementPrevu,
                         LocalDate datePaiementEffectue, Integer idMethodePaiement, Integer idStatus, String statusLibelle) {
        this.idReservation = idReservation;
        this.client = client;
        this.idSeance = idSeance;
        this.dateReservation = dateReservation;
        this.total = total;
        this.datePaiementPrevu = datePaiementPrevu;
        this.datePaiementEffectue = datePaiementEffectue;
        this.idMethodePaiement = idMethodePaiement;
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

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDate getDatePaiementPrevu() {
        return datePaiementPrevu;
    }

    public void setDatePaiementPrevu(LocalDate datePaiementPrevu) {
        this.datePaiementPrevu = datePaiementPrevu;
    }

    public LocalDate getDatePaiementEffectue() {
        return datePaiementEffectue;
    }

    public void setDatePaiementEffectue(LocalDate datePaiementEffectue) {
        this.datePaiementEffectue = datePaiementEffectue;
    }

    public Integer getIdMethodePaiement() {
        return idMethodePaiement;
    }

    public void setIdMethodePaiement(Integer idMethodePaiement) {
        this.idMethodePaiement = idMethodePaiement;
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
