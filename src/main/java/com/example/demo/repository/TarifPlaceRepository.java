package com.example.demo.repository;

import com.example.demo.entity.TarifPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifPlaceRepository extends JpaRepository<TarifPlace, Integer> {
}