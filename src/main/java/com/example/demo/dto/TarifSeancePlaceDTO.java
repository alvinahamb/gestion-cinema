package com.example.demo.dto;

import java.math.BigDecimal;

public class TarifSeancePlaceDTO {
    private Integer id;
    private Integer idSeance;
    private SeanceDTO seance;
    private Integer idCategorieClient;
    private CategorieClientDTO categorieClient;
    private Integer idTypePlace;
    private TypePlaceDTO typePlace;
    private BigDecimal tarif;

    public TarifSeancePlaceDTO() {}

    public TarifSeancePlaceDTO(Integer id, SeanceDTO seance, CategorieClientDTO categorieClient, 
                              TypePlaceDTO typePlace, BigDecimal tarif) {
        this.id = id;
        this.seance = seance;
        this.categorieClient = categorieClient;
        this.typePlace = typePlace;
        this.tarif = tarif;
        this.idSeance = seance != null ? seance.getIdSeance() : null;
        this.idCategorieClient = categorieClient != null ? categorieClient.getIdCategorieClient() : null;
        this.idTypePlace = typePlace != null ? typePlace.getIdTypePlace() : null;
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

    public Integer getIdCategorieClient() {
        return idCategorieClient;
    }

    public void setIdCategorieClient(Integer idCategorieClient) {
        this.idCategorieClient = idCategorieClient;
    }

    public CategorieClientDTO getCategorieClient() {
        return categorieClient;
    }

    public void setCategorieClient(CategorieClientDTO categorieClient) {
        this.categorieClient = categorieClient;
        this.idCategorieClient = categorieClient != null ? categorieClient.getIdCategorieClient() : null;
    }

    public Integer getIdTypePlace() {
        return idTypePlace;
    }

    public void setIdTypePlace(Integer idTypePlace) {
        this.idTypePlace = idTypePlace;
    }

    public TypePlaceDTO getTypePlace() {
        return typePlace;
    }

    public void setTypePlace(TypePlaceDTO typePlace) {
        this.typePlace = typePlace;
        this.idTypePlace = typePlace != null ? typePlace.getIdTypePlace() : null;
    }

    public BigDecimal getTarif() {
        return tarif;
    }

    public void setTarif(BigDecimal tarif) {
        this.tarif = tarif;
    }
}