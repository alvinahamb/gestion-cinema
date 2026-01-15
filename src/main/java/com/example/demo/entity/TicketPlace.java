package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ticket_place")
public class TicketPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_ticket", nullable = false)
    private Ticket ticket;
    
    @ManyToOne
    @JoinColumn(name = "id_place", nullable = false)
    private Place place;
    
    @ManyToOne
    @JoinColumn(name = "id_categorie_client")
    private CategorieClient categorieClient;
    
    private BigDecimal prix;

    public TicketPlace() {}

    public TicketPlace(Ticket ticket, Place place, CategorieClient categorieClient, BigDecimal prix) {
        this.ticket = ticket;
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

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
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
