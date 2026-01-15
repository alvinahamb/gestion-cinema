package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tarif_place")
public class TarifPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTarif;
    
    @ManyToOne
    @JoinColumn(name = "id_type_place")
    private TypePlace typePlace;
    
    private BigDecimal prix;

    public TarifPlace() {}

    public TarifPlace(TypePlace typePlace, BigDecimal prix) {
        this.typePlace = typePlace;
        this.prix = prix;
    }

    public Integer getIdTarif() {
        return idTarif;
    }

    public void setIdTarif(Integer idTarif) {
        this.idTarif = idTarif;
    }

    public TypePlace getTypePlace() {
        return typePlace;
    }

    public void setTypePlace(TypePlace typePlace) {
        this.typePlace = typePlace;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }
}