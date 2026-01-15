package com.example.demo.dto;

import java.time.LocalDate;

public class SeanceStatusHistoriqueDTO {
    private Integer id;
    private Integer idSeance;
    private SeanceDTO seance;
    private Integer idStatus;
    private StatusDTO status;
    private LocalDate dateModification;

    public SeanceStatusHistoriqueDTO() {}

    public SeanceStatusHistoriqueDTO(Integer id, SeanceDTO seance, StatusDTO status, LocalDate dateModification) {
        this.id = id;
        this.seance = seance;
        this.status = status;
        this.dateModification = dateModification;
        this.idSeance = seance != null ? seance.getIdSeance() : null;
        this.idStatus = status != null ? status.getIdStatus() : null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(Integer idSeance) {
        this.idSeance = idSeance;
    }

    public SeanceDTO getSeance() {
        return seance;
    }

    public void setSeance(SeanceDTO seance) {
        this.seance = seance;
        this.idSeance = seance != null ? seance.getIdSeance() : null;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
        this.idStatus = status != null ? status.getIdStatus() : null;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }
}