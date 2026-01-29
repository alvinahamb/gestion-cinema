package com.example.demo.repository;

import com.example.demo.entity.VenteProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VenteProduitRepository extends JpaRepository<VenteProduit, Integer> {
    @Query("SELECT v FROM VenteProduit v WHERE v.seance.idSeance = :seanceId")
    List<VenteProduit> findBySeanceId(@Param("seanceId") Integer seanceId);
    
    @Query("SELECT COALESCE(SUM(v.total), 0) FROM VenteProduit v WHERE v.seance.idSeance = :seanceId")
    BigDecimal sumTotalBySeanceId(@Param("seanceId") Integer seanceId);
    
    @Query("SELECT COALESCE(SUM(v.total), 0) FROM VenteProduit v")
    BigDecimal sumAllVenteProduits();
}
