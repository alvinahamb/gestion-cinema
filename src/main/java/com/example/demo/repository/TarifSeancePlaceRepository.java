package com.example.demo.repository;

import com.example.demo.entity.TarifSeancePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifSeancePlaceRepository extends JpaRepository<TarifSeancePlace, Integer> {
}
