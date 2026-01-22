package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "diffusion")
public class Diffusion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_societe")
    private Societe societe;
    
    @ManyToOne
    @JoinColumn(name = "id_pub")
    private Publicite publicite;
    
    @ManyToOne
    @JoinColumn(name = "id_tarif")
    private TarifDiffusion tarifDiffusion;
    
    private Integer mois;
    
    private Integer annee;
    
    private LocalDate dateDiffusion;
    
    private Integer nombre;
    
    private Integer diffAttribue;
    
    private Integer diffRestant;
    
    private LocalDate dateAjout;
    
    @OneToMany(mappedBy = "diffusion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaiementSociete> paiementsSociete;

    public Diffusion() {}

    public Diffusion(Societe societe, Publicite publicite, TarifDiffusion tarifDiffusion, 
                     Integer mois, Integer annee, LocalDate dateDiffusion, Integer nombre,
                     Integer diffAttribue, Integer diffRestant, LocalDate dateAjout) {
        this.societe = societe;
        this.publicite = publicite;
        this.tarifDiffusion = tarifDiffusion;
        this.mois = mois;
        this.annee = annee;
        this.dateDiffusion = dateDiffusion;
        this.nombre = nombre;
        this.diffAttribue = diffAttribue;
        this.diffRestant = diffRestant;
        this.dateAjout = dateAjout;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Societe getSociete() {
        return societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public Publicite getPublicite() {
        return publicite;
    }

    public void setPublicite(Publicite publicite) {
        this.publicite = publicite;
    }

    public TarifDiffusion getTarifDiffusion() {
        return tarifDiffusion;
    }

    public void setTarifDiffusion(TarifDiffusion tarifDiffusion) {
        this.tarifDiffusion = tarifDiffusion;
    }

    public Integer getMois() {
        return mois;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public LocalDate getDateDiffusion() {
        return dateDiffusion;
    }

    public void setDateDiffusion(LocalDate dateDiffusion) {
        this.dateDiffusion = dateDiffusion;
    }

    public Integer getNombre() {
        return nombre;
    }

    public void setNombre(Integer nombre) {
        this.nombre = nombre;
    }

    public Integer getDiffAttribue() {
        return diffAttribue;
    }

    public void setDiffAttribue(Integer diffAttribue) {
        this.diffAttribue = diffAttribue;
    }

    public Integer getDiffRestant() {
        return diffRestant;
    }

    public void setDiffRestant(Integer diffRestant) {
        this.diffRestant = diffRestant;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public List<PaiementSociete> getPaiementsSociete() {
        return paiementsSociete;
    }

    public void setPaiementsSociete(List<PaiementSociete> paiementsSociete) {
        this.paiementsSociete = paiementsSociete;
    }
}
