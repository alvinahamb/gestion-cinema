package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "reservation_place")
public class ReservationPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_reservation", nullable = false)
    private Reservation reservation;
    
    @ManyToOne
    @JoinColumn(name = "id_place", nullable = false)
    private Place place;
    
    @ManyToOne
    @JoinColumn(name = "id_categorie_client")
    private CategorieClient categorieClient;
    
    private BigDecimal prix;

    public ReservationPlace() {}

    public ReservationPlace(Reservation reservation, Place place, CategorieClient categorieClient, BigDecimal prix) {
        this.reservation = reservation;
        this.place = place;
        this.categorieClient = categorieClient;
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

    public CategorieClient getCategorieClient() {
        return categorieClient;
    }

    public void setCategorieClient(CategorieClient categorieClient) {
        this.categorieClient = categorieClient;
    }
}
