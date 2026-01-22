package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TarifDiffusionDTO {
    private Integer id;
    private BigDecimal tarif;
    private LocalDate dateAjout;

    public TarifDiffusionDTO() {}

    public TarifDiffusionDTO(Integer id, BigDecimal tarif, LocalDate dateAjout) {
        this.id = id;
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
}
