package com.example.demo.dto;

import java.math.BigDecimal;

public class RemiseCategorieDTO {
    private Integer idRemise;
    private Integer idTypePlace;
    private Integer idCategorieClient;
    private BigDecimal tarif;
    private BigDecimal pourcentageDeReduction;

    public RemiseCategorieDTO() {}

    public RemiseCategorieDTO(Integer idRemise, Integer idTypePlace, Integer idCategorieClient, BigDecimal tarif, BigDecimal pourcentageDeReduction) {
        this.idRemise = idRemise;
        this.idTypePlace = idTypePlace;
        this.idCategorieClient = idCategorieClient;
        this.tarif = tarif;
        this.pourcentageDeReduction = pourcentageDeReduction;
    }

    public Integer getIdRemise() {
        return idRemise;
    }

    public void setIdRemise(Integer idRemise) {
        this.idRemise = idRemise;
    }

    public Integer getIdTypePlace() {
        return idTypePlace;
    }

    public void setIdTypePlace(Integer idTypePlace) {
        this.idTypePlace = idTypePlace;
    }

    public Integer getIdCategorieClient() {
        return idCategorieClient;
    }

    public void setIdCategorieClient(Integer idCategorieClient) {
        this.idCategorieClient = idCategorieClient;
    }

    public BigDecimal getTarif() {
        return tarif;
    }

    public void setTarif(BigDecimal tarif) {
        this.tarif = tarif;
    }

    public BigDecimal getPourcentageDeReduction() {
        return pourcentageDeReduction;
    }

    public void setPourcentageDeReduction(BigDecimal pourcentageDeReduction) {
        this.pourcentageDeReduction = pourcentageDeReduction;
    }
}
