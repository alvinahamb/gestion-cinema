package com.example.demo.repository;

import com.example.demo.entity.FactureSocieteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FactureSocieteDetailRepository extends JpaRepository<FactureSocieteDetail, Integer> {
    
    List<FactureSocieteDetail> findByFactureSocieteId(Integer idFactureSociete);
    
    @Query("SELECT COALESCE(SUM(fsd.montant), 0) FROM FactureSocieteDetail fsd WHERE fsd.factureSociete.id = :idFactureSociete")
    BigDecimal sumMontantByFactureSocieteId(@Param("idFactureSociete") Integer idFactureSociete);
    
    @Query("SELECT COALESCE(SUM(fsd.pourcentagePaye), 0) FROM FactureSocieteDetail fsd WHERE fsd.factureSociete.id = :idFactureSociete")
    BigDecimal sumPourcentagePayeByFactureSocieteId(@Param("idFactureSociete") Integer idFactureSociete);
}
