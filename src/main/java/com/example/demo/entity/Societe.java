package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "societe")
public class Societe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nom;
    
    private LocalDate dateAjout;
    
    @Column(length = 20)
    private String telephone;
    
    @Column(nullable = false, length = 100)
    private String adresse;
    
    @OneToMany(mappedBy = "societe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diffusion> diffusions;

    public Societe() {}

    public Societe(String nom, LocalDate dateAjout, String telephone, String adresse) {
        this.nom = nom;
        this.dateAjout = dateAjout;
        this.telephone = telephone;
        this.adresse = adresse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Diffusion> getDiffusions() {
        return diffusions;
    }

    public void setDiffusions(List<Diffusion> diffusions) {
        this.diffusions = diffusions;
    }
}
