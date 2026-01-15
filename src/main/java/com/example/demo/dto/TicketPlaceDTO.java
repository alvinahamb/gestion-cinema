package com.example.demo.dto;

import java.math.BigDecimal;

public class TicketPlaceDTO {
    private Integer id;
    private Integer idTicket;
    private TicketDTO ticket;
    private Integer idPlace;
    private PlaceDTO place;
    private Integer idCategorieClient;
    private CategorieClientDTO categorieClient;
    private BigDecimal prix;

    public TicketPlaceDTO() {}

    public TicketPlaceDTO(Integer id, TicketDTO ticket, PlaceDTO place, CategorieClientDTO categorieClient, BigDecimal prix) {
        this.id = id;
        this.ticket = ticket;
        this.place = place;
        this.categorieClient = categorieClient;
        this.prix = prix;
        this.idTicket = ticket != null ? ticket.getIdTicket() : null;
        this.idPlace = place != null ? place.getIdPlace() : null;
        this.idCategorieClient = categorieClient != null ? categorieClient.getIdCategorieClient() : null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public TicketDTO getTicket() {
        return ticket;
    }

    public void setTicket(TicketDTO ticket) {
        this.ticket = ticket;
        this.idTicket = ticket != null ? ticket.getIdTicket() : null;
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