package com.example.demo.repository;

import com.example.demo.entity.PaiementProduitDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaiementProduitDetailRepository extends JpaRepository<PaiementProduitDetail, Integer> {
    @Query("SELECT p FROM PaiementProduitDetail p WHERE p.paiementProduit.id = :paiementId")
    List<PaiementProduitDetail> findByPaiementProduitId(@Param("paiementId") Integer paiementId);
}
