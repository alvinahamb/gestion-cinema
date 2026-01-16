package com.example.demo.repository;

import com.example.demo.entity.RemiseCategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RemiseCategorieRepository extends JpaRepository<RemiseCategorie, Integer> {
    @Query("SELECT r FROM RemiseCategorie r WHERE r.typePlace.idTypePlace = ?1 AND r.categorieClient.idCategorieClient = ?2")
    Optional<RemiseCategorie> findByTypePlaceAndCategorie(Integer idTypePlace, Integer idCategorieClient);
    
    List<RemiseCategorie> findByTypePlaceIdTypePlace(Integer idTypePlace);
    List<RemiseCategorie> findByCategorieClientIdCategorieClient(Integer idCategorieClient);
}
