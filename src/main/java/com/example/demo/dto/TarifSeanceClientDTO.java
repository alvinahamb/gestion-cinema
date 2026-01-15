package com.example.demo.dto;

import java.math.BigDecimal;

public class TarifSeanceClientDTO {
    private Integer id;
    private SeanceDTO seance;
    private CategorieClientDTO categorieClient;
    private BigDecimal tarif;

    public TarifSeanceClientDTO() {}

    public TarifSeanceClientDTO(Integer id, SeanceDTO seance, CategorieClientDTO categorieClient, BigDecimal tarif) {
        this.id = id;
        this.seance = seance;
        this.categorieClient = categorieClient;
        this.tarif = tarif;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SeanceDTO getSeance() {
        return seance;
    }

    public Integer getIdSeance() {
        return seance != null ? seance.getIdSeance() : null;
    }

    public void setSeance(SeanceDTO seance) {
        this.seance = seance;
    }

    public CategorieClientDTO getCategorieClient() {
        return categorieClient;
    }

    public Integer getIdCategorieClient() {
        return categorieClient != null ? categorieClient.getIdCategorieClient() : null;
    }

    public void setCategorieClient(CategorieClientDTO categorieClient) {
        this.categorieClient = categorieClient;
    }

    public BigDecimal getTarif() {
        return tarif;
    }

    public void setTarif(BigDecimal tarif) {
        this.tarif = tarif;
    }
}