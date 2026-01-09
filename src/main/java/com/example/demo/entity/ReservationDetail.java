package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "reservation_detail")
public class ReservationDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;
    
    @ManyToOne
    @JoinColumn(name = "id_seance")
    private Seance seance;
    
    @ManyToOne
    @JoinColumn(name = "id_place")
    private Place place;
    
    private BigDecimal prix;

    public ReservationDetail() {}

    public ReservationDetail(Reservation reservation, Seance seance, Place place, BigDecimal prix) {
        this.reservation = reservation;
        this.seance = seance;
        this.place = place;
        this.prix = prix;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }
}
