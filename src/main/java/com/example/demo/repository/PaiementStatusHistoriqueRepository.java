package com.example.demo.repository;

import com.example.demo.entity.PaiementStatusHistorique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementStatusHistoriqueRepository extends JpaRepository<PaiementStatusHistorique, Integer> {
}
