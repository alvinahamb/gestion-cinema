package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStatus;
    
    @Column(nullable = false, length = 100)
    private String libelle;
    
    @Column(nullable = false, length = 100)
    private String code;
    
    @Column(nullable = false)
    private Integer ordre;
    
    @ManyToOne
    @JoinColumn(name = "id_type_statut")
    private TypeStatut typeStatut;

    public Status() {}

    public Status(String libelle, String code, Integer ordre, TypeStatut typeStatut) {
        this.libelle = libelle;
        this.code = code;
        this.ordre = ordre;
        this.typeStatut = typeStatut;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public TypeStatut getTypeStatut() {
        return typeStatut;
    }

    public void setTypeStatut(TypeStatut typeStatut) {
        this.typeStatut = typeStatut;
    }
}
