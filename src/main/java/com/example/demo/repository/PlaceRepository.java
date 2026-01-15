package com.example.demo.repository;

import com.example.demo.entity.Place;
import com.example.demo.entity.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {
    List<Place> findBySalle(Salle salle);
    List<Place> findBySalleIdSalle(Integer salleId);
}
