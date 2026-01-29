package com.example.demo.repository;

import com.example.demo.entity.VenteProduitDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenteProduitDetailRepository extends JpaRepository<VenteProduitDetail, Integer> {
    @Query("SELECT v FROM VenteProduitDetail v WHERE v.venteProduit.id = :venteId")
    List<VenteProduitDetail> findByVenteProduitId(@Param("venteId") Integer venteId);
}
