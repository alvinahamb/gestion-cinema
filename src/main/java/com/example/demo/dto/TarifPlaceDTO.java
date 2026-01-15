package com.example.demo.dto;

import java.math.BigDecimal;

public class TarifPlaceDTO {
    private Integer idTarif;
    private Integer idTypePlace;
    private BigDecimal prix;

    public TarifPlaceDTO() {}

    public TarifPlaceDTO(Integer idTarif, Integer idTypePlace, BigDecimal prix) {
        this.idTarif = idTarif;
        this.idTypePlace = idTypePlace;
        this.prix = prix;
    }

    public Integer getIdTarif() {
        return idTarif;
    }

    public void setIdTarif(Integer idTarif) {
        this.idTarif = idTarif;
    }

    public Integer getIdTypePlace() {
        return idTypePlace;
    }

    public void setIdTypePlace(Integer idTypePlace) {
        this.idTypePlace = idTypePlace;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }
}