package com.example.demo.repository;

import com.example.demo.entity.TypePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePlaceRepository extends JpaRepository<TypePlace, Integer> {
}
