package com.example.demo.repository;

import com.example.demo.entity.CategorieClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieClientRepository extends JpaRepository<CategorieClient, Integer> {
}