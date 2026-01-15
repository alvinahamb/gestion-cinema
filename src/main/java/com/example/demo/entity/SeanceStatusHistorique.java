package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seance_status_historique")
public class SeanceStatusHistorique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_seance")
    private Seance seance;
    
    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;
    
    private LocalDate dateModification;

    public SeanceStatusHistorique() {}

    public SeanceStatusHistorique(Seance seance, Status status, LocalDate dateModification) {
        this.seance = seance;
        this.status = status;
        this.dateModification = dateModification;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }
}
