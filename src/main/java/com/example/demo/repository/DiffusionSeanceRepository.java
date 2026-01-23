package com.example.demo.repository;

import com.example.demo.entity.DiffusionSeance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DiffusionSeanceRepository extends JpaRepository<DiffusionSeance, Integer> {
    
    List<DiffusionSeance> findBySeanceIdSeance(Integer idSeance);
    
    /**
     * Calcule le montant généré par les diffusions de pub pour une séance donnée.
     * Formule: SUM(tarif_diffusion.tarif * nombre de diffusions pour cette séance)
     */
    @Query("SELECT COALESCE(SUM(d.tarifDiffusion.tarif * ds.nombre), 0) FROM DiffusionSeance ds " +
           "JOIN ds.diffusion d " +
           "WHERE ds.seance.idSeance = :idSeance")
    BigDecimal sumMontantDiffusionBySeanceId(@Param("idSeance") Integer idSeance);
    
    /**
     * Compte le nombre de diffusions pour une séance donnée
     */
    @Query("SELECT COUNT(ds) FROM DiffusionSeance ds WHERE ds.seance.idSeance = :idSeance")
    Long countBySeanceId(@Param("idSeance") Integer idSeance);
}
