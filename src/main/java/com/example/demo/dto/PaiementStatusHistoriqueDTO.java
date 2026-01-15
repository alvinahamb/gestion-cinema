package com.example.demo.dto;

import java.time.LocalDate;

public class PaiementStatusHistoriqueDTO {
    private Integer id;
    private Integer idPaiement;
    private PaiementDTO paiement;
    private Integer idStatus;
    private StatusDTO status;
    private LocalDate dateModification;

    public PaiementStatusHistoriqueDTO() {}

    public PaiementStatusHistoriqueDTO(Integer id, PaiementDTO paiement, StatusDTO status, LocalDate dateModification) {
        this.id = id;
        this.paiement = paiement;
        this.status = status;
        this.dateModification = dateModification;
        this.idPaiement = paiement != null ? paiement.getIdPaiement() : null;
        this.idStatus = status != null ? status.getIdStatus() : null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(Integer idPaiement) {
        this.idPaiement = idPaiement;
    }

    public PaiementDTO getPaiement() {
        return paiement;
    }

    public void setPaiement(PaiementDTO paiement) {
        this.paiement = paiement;
        this.idPaiement = paiement != null ? paiement.getIdPaiement() : null;
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