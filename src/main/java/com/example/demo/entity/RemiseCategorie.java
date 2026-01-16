package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "remise_categorie")
public class RemiseCategorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRemise;
    
    @ManyToOne
    @JoinColumn(name = "id_type_place")
    private TypePlace typePlace;
    
    @ManyToOne
    @JoinColumn(name = "id_categorie_client")
    private CategorieClient categorieClient;
    
    private BigDecimal tarif;
    
    private BigDecimal pourcentageDeReduction;

    public RemiseCategorie() {}

    public RemiseCategorie(TypePlace typePlace, CategorieClient categorieClient, BigDecimal tarif, BigDecimal pourcentageDeReduction) {
        this.typePlace = typePlace;
        this.categorieClient = categorieClient;
        this.tarif = tarif;
        this.pourcentageDeReduction = pourcentageDeReduction;
    }

    public Integer getIdRemise() {
        return idRemise;
    }

    public void setIdRemise(Integer idRemise) {
        this.idRemise = idRemise;
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

    public BigDecimal getPourcentageDeReduction() {
        return pourcentageDeReduction;
    }

    public void setPourcentageDeReduction(BigDecimal pourcentageDeReduction) {
        this.pourcentageDeReduction = pourcentageDeReduction;
    }
}
