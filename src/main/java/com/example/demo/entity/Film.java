package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFilm;
    
    @Column(nullable = false, length = 200)
    private String titre;
    
    @Column(columnDefinition = "TEXT")
    private String synopsis;
    
    @Column(nullable = false)
    private Integer duree;
    
    @ManyToOne
    @JoinColumn(name = "id_langue")
    private Langue langue;
    
    @ManyToOne
    @JoinColumn(name = "id_pays")
    private Pays pays;
    
    private LocalDate dateSortie;
    
    private LocalDate dateAjout;
    
    private Integer classificationAge;
    
    @ManyToOne
    @JoinColumn(name = "id_status")
    private StatusFilm statusFilm;

    public Film() {}

    public Film(String titre, String synopsis, Integer duree, Langue langue, Pays pays, 
                LocalDate dateSortie, LocalDate dateAjout, Integer classificationAge, StatusFilm statusFilm) {
        this.titre = titre;
        this.synopsis = synopsis;
        this.duree = duree;
        this.langue = langue;
        this.pays = pays;
        this.dateSortie = dateSortie;
        this.dateAjout = dateAjout;
        this.classificationAge = classificationAge;
        this.statusFilm = statusFilm;
    }

    public Integer getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(Integer idFilm) {
        this.idFilm = idFilm;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Langue getLangue() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public LocalDate getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Integer getClassificationAge() {
        return classificationAge;
    }

    public void setClassificationAge(Integer classificationAge) {
        this.classificationAge = classificationAge;
    }

    public StatusFilm getStatusFilm() {
        return statusFilm;
    }

    public void setStatusFilm(StatusFilm statusFilm) {
        this.statusFilm = statusFilm;
    }
}
