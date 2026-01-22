package com.example.demo.repository;

import com.example.demo.entity.PaiementSociete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PaiementSocieteRepository extends JpaRepository<PaiementSociete, Integer> {
    List<PaiementSociete> findByDiffusionId(Integer diffusionId);
    
    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM PaiementSociete p WHERE p.diffusion.id = :diffusionId")
    BigDecimal sumMontantByDiffusionId(@Param("diffusionId") Integer diffusionId);
}
