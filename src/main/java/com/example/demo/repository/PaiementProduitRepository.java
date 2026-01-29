package com.example.demo.repository;

import com.example.demo.entity.PaiementProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaiementProduitRepository extends JpaRepository<PaiementProduit, Integer> {
    @Query("SELECT p FROM PaiementProduit p WHERE p.venteProduit.id = :venteId")
    Optional<PaiementProduit> findByVenteProduitId(@Param("venteId") Integer venteId);
    
    @Query("SELECT p FROM PaiementProduit p LEFT JOIN FETCH p.venteProduit")
    List<PaiementProduit> findAllWithVenteProduit();
}
