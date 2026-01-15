package com.example.demo.repository;

import com.example.demo.entity.TypeStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeStatutRepository extends JpaRepository<TypeStatut, Integer> {
}