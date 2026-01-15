package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tarif_seance_client")
public class TarifSeanceClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_seance")
    private Seance seance;

    @ManyToOne
    @JoinColumn(name = "id_categorie_client")
    private CategorieClient categorieClient;

    private BigDecimal tarif;

    public TarifSeanceClient() {}

    public TarifSeanceClient(Seance seance, CategorieClient categorieClient, BigDecimal tarif) {
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

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public CategorieClient getCategorieClient() {
        return categorieClient;
    }

    public void setCategorieClient(CategorieClient categorieClient) {
        this.categorieClient = categorieClient;
    }

    public BigDecimal getTarif() {
        return tarif;
    }

    public void setTarif(BigDecimal tarif) {
        this.tarif = tarif;
    }
}