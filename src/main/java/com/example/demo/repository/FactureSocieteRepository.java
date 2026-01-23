package com.example.demo.repository;

import com.example.demo.entity.FactureSociete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface FactureSocieteRepository extends JpaRepository<FactureSociete, Integer> {
    
    List<FactureSociete> findBySocieteId(Integer idSociete);
    
    /**
     * Calcule la somme des montants payés pour les diffusions liées à une séance.
     * Total payé = SUM(nombre * tarif * (100 - pourcentage_restante) / 100) pour chaque diffusion_seance
     */
    @Query("SELECT COALESCE(SUM(ds.nombre * td.tarif * (100 - fs.pourcentageRestante) / 100), 0) " +
           "FROM DiffusionSeance ds " +
           "JOIN ds.diffusion d " +
           "JOIN d.tarifDiffusion td " +
           "JOIN FactureSociete fs ON fs.societe.id = d.societe.id " +
           "WHERE ds.seance.idSeance = :idSeance")
    BigDecimal sumTotalPayeBySeanceId(@Param("idSeance") Integer idSeance);
    
    /**
     * Calcule la somme des montants restants pour les diffusions liées à une séance.
     * Total restant = SUM(nombre * tarif * pourcentage_restante / 100) pour chaque diffusion_seance
     */
    @Query("SELECT COALESCE(SUM(ds.nombre * td.tarif * fs.pourcentageRestante / 100), 0) " +
           "FROM DiffusionSeance ds " +
           "JOIN ds.diffusion d " +
           "JOIN d.tarifDiffusion td " +
           "JOIN FactureSociete fs ON fs.societe.id = d.societe.id " +
           "WHERE ds.seance.idSeance = :idSeance")
    BigDecimal sumTotalRestantBySeanceId(@Param("idSeance") Integer idSeance);
}
