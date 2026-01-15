package com.example.demo.repository;

import com.example.demo.entity.PaiementDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementDetailRepository extends JpaRepository<PaiementDetail, Integer> {
}
