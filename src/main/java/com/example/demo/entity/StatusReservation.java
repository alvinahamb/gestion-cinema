package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "status_reservation")
public class StatusReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStatus;
    
    @Column(nullable = false)
    private String libelle;

    public StatusReservation() {}

    public StatusReservation(String libelle) {
        this.libelle = libelle;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
