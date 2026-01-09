package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "achat_detail")
public class AchatDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAchatDetail;
    
    @ManyToOne
    @JoinColumn(name = "id_achat")
    private Achat achat;
    
    @ManyToOne
    @JoinColumn(name = "id_seance")
    private Seance seance;
    
    @ManyToOne
    @JoinColumn(name = "id_place")
    private Place place;
    
    private BigDecimal prix;

    public AchatDetail() {}

    public AchatDetail(Achat achat, Seance seance, Place place, BigDecimal prix) {
        this.achat = achat;
        this.seance = seance;
        this.place = place;
        this.prix = prix;
    }

    public Integer getIdAchatDetail() {
        return idAchatDetail;
    }

    public void setIdAchatDetail(Integer idAchatDetail) {
        this.idAchatDetail = idAchatDetail;
    }

    public Achat getAchat() {
        return achat;
    }

    public void setAchat(Achat achat) {
        this.achat = achat;
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
