package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tarif_diffusion")
public class TarifDiffusion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(precision = 12, scale = 2)
    private BigDecimal tarif;
    
    private LocalDate dateAjout;
    
    @OneToMany(mappedBy = "tarifDiffusion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diffusion> diffusions;

    public TarifDiffusion() {}

    public TarifDiffusion(BigDecimal tarif, LocalDate dateAjout) {
        this.tarif = tarif;
        this.dateAjout = dateAjout;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTarif() {
        return tarif;
    }

    public void setTarif(BigDecimal tarif) {
        this.tarif = tarif;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public List<Diffusion> getDiffusions() {
        return diffusions;
    }

    public void setDiffusions(List<Diffusion> diffusions) {
        this.diffusions = diffusions;
    }
}
