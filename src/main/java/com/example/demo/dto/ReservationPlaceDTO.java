package com.example.demo.dto;

import java.math.BigDecimal;

public class ReservationPlaceDTO {
    private Integer id;
    private Integer idReservation;
    private ReservationDTO reservation;
    private Integer idPlace;
    private PlaceDTO place;
    private Integer idCategorieClient;
    private CategorieClientDTO categorieClient;
    private BigDecimal prix;

    public ReservationPlaceDTO() {}

    public ReservationPlaceDTO(Integer id, ReservationDTO reservation, PlaceDTO place, CategorieClientDTO categorieClient, BigDecimal prix) {
        this.id = id;
        this.reservation = reservation;
        this.place = place;
        this.categorieClient = categorieClient;
        this.prix = prix;
        this.idReservation = reservation != null ? reservation.getIdReservation() : null;
        this.idPlace = place != null ? place.getIdPlace() : null;
        this.idCategorieClient = categorieClient != null ? categorieClient.getIdCategorieClient() : null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Integer idReservation) {
        this.idReservation = idReservation;
    }

    public ReservationDTO getReservation() {
        return reservation;
    }

    public void setReservation(ReservationDTO reservation) {
        this.reservation = reservation;
        this.idReservation = reservation != null ? reservation.getIdReservation() : null;
    }

    public Integer getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(Integer idPlace) {
        this.idPlace = idPlace;
    }

    public PlaceDTO getPlace() {
        return place;
    }

    public void setPlace(PlaceDTO place) {
        this.place = place;
        this.idPlace = place != null ? place.getIdPlace() : null;
    }

    public Integer getIdCategorieClient() {
        return idCategorieClient;
    }

    public void setIdCategorieClient(Integer idCategorieClient) {
        this.idCategorieClient = idCategorieClient;
    }

    public CategorieClientDTO getCategorieClient() {
        return categorieClient;
    }

    public void setCategorieClient(CategorieClientDTO categorieClient) {
        this.categorieClient = categorieClient;
        this.idCategorieClient = categorieClient != null ? categorieClient.getIdCategorieClient() : null;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }
}