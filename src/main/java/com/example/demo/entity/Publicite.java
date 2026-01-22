package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "publicite")
public class Publicite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String titre;
    
    @Column(nullable = false)
    private Integer duree;
    
    private LocalDate dateAjout;
    
    @Column(nullable = false, length = 100)
    private String code;
    
    @OneToMany(mappedBy = "publicite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diffusion> diffusions;

    public Publicite() {}

    public Publicite(String titre, Integer duree, LocalDate dateAjout, String code) {
        this.titre = titre;
        this.duree = duree;
        this.dateAjout = dateAjout;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Diffusion> getDiffusions() {
        return diffusions;
    }

    public void setDiffusions(List<Diffusion> diffusions) {
        this.diffusions = diffusions;
    }
}
