package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "diffusion_seance")
public class DiffusionSeance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_diffusion")
    private Diffusion diffusion;
    
    @ManyToOne
    @JoinColumn(name = "id_seance")
    private Seance seance;
    
    private Integer nombre;
    
    private LocalDate dateAjout;

    public DiffusionSeance() {}

    public DiffusionSeance(Diffusion diffusion, Seance seance, Integer nombre, LocalDate dateAjout) {
        this.diffusion = diffusion;
        this.seance = seance;
        this.nombre = nombre;
        this.dateAjout = dateAjout;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Diffusion getDiffusion() {
        return diffusion;
    }

    public void setDiffusion(Diffusion diffusion) {
        this.diffusion = diffusion;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Integer getNombre() {
        return nombre;
    }

    public void setNombre(Integer nombre) {
        this.nombre = nombre;
    }
}
