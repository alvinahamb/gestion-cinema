package com.example.demo.dto;

import java.time.LocalDate;

public class ClientDTO {
    private Integer idClient;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String email;
    private String telephone;
    private CategorieClientDTO categorieClient;
    private Integer categorieClientId;

    public ClientDTO() {}

    public ClientDTO(Integer idClient, String nom, String prenom, LocalDate dateNaissance, String email, String telephone, CategorieClientDTO categorieClient) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.telephone = telephone;
        this.categorieClient = categorieClient;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public CategorieClientDTO getCategorieClient() {
        return categorieClient;
    }

    public void setCategorieClient(CategorieClientDTO categorieClient) {
        this.categorieClient = categorieClient;
    }

    public Integer getCategorieClientId() {
        return categorieClientId;
    }

    public void setCategorieClientId(Integer categorieClientId) {
        this.categorieClientId = categorieClientId;
    }
}
