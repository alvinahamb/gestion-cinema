package com.example.demo.dto;

public class StatusDTO {
    private Integer idStatus;
    private String libelle;
    private String code;
    private Integer ordre;
    private TypeStatutDTO typeStatut;

    public StatusDTO() {}

    public StatusDTO(Integer idStatus, String libelle, String code, Integer ordre, TypeStatutDTO typeStatut) {
        this.idStatus = idStatus;
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

    public TypeStatutDTO getTypeStatut() {
        return typeStatut;
    }

    public void setTypeStatut(TypeStatutDTO typeStatut) {
        this.typeStatut = typeStatut;
    }
}
