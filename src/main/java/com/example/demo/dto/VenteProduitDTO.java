package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VenteProduitDTO {
    private Integer id;
    private ClientDTO client;
    private Integer clientId;
    private SeanceDTO seance;
    private Integer seanceId;
    private BigDecimal total;
    private LocalDate dateAchat;
    private List<VenteProduitDetailDTO> details;

    public VenteProduitDTO() {}

    public VenteProduitDTO(Integer id, ClientDTO client, SeanceDTO seance, BigDecimal total, LocalDate dateAchat) {
        this.id = id;
        this.client = client;
        this.seance = seance;
        this.total = total;
        this.dateAchat = dateAchat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public SeanceDTO getSeance() {
        return seance;
    }

    public void setSeance(SeanceDTO seance) {
        this.seance = seance;
    }

    public Integer getSeanceId() {
        return seanceId;
    }

    public void setSeanceId(Integer seanceId) {
        this.seanceId = seanceId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
    }

    public List<VenteProduitDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<VenteProduitDetailDTO> details) {
        this.details = details;
    }
}
