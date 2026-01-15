package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tarif_seance_client_place")
public class TarifSeancePlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_seance")
    private Seance seance;
    
    @ManyToOne
    @JoinColumn(name = "id_categorie_client")
    private CategorieClient categorieClient;
    
    @ManyToOne
    @JoinColumn(name = "id_type_place")
    private TypePlace typePlace;
    
    private BigDecimal tarif;

    public TarifSeancePlace() {}

    public TarifSeancePlace(Seance seance, CategorieClient categorieClient, TypePlace typePlace, BigDecimal tarif) {
        this.seance = seance;
        this.categorieClient = categorieClient;
        this.typePlace = typePlace;
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

    public TypePlace getTypePlace() {
        return typePlace;
    }

    public void setTypePlace(TypePlace typePlace) {
        this.typePlace = typePlace;
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
