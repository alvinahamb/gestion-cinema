package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
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
    
    private LocalDate dateReservation;
    
    private BigDecimal total;
    
    private LocalDate datePaiementPrevu;
    
    private LocalDate datePaiementEffectue;
    
    @ManyToOne
    @JoinColumn(name = "id_methode_paiement")
    private MethodePaiement methodePaiement;
    
    @ManyToOne
    @JoinColumn(name = "id_status")
    private StatusReservation statusReservation;

    public Reservation() {}

    public Reservation(Client client, Seance seance, LocalDate dateReservation, BigDecimal total,
                      LocalDate datePaiementPrevu, LocalDate datePaiementEffectue, 
                      MethodePaiement methodePaiement, StatusReservation statusReservation) {
        this.client = client;
        this.seance = seance;
        this.dateReservation = dateReservation;
        this.total = total;
        this.datePaiementPrevu = datePaiementPrevu;
        this.datePaiementEffectue = datePaiementEffectue;
        this.methodePaiement = methodePaiement;
        this.statusReservation = statusReservation;
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

    public MethodePaiement getMethodePaiement() {
        return methodePaiement;
    }

    public void setMethodePaiement(MethodePaiement methodePaiement) {
        this.methodePaiement = methodePaiement;
    }

    public StatusReservation getStatusReservation() {
        return statusReservation;
    }

    public void setStatusReservation(StatusReservation statusReservation) {
        this.statusReservation = statusReservation;
    }
}
