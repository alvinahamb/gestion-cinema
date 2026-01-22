package com.example.demo.repository;

import com.example.demo.entity.Diffusion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiffusionRepository extends JpaRepository<Diffusion, Integer> {
    List<Diffusion> findBySocieteId(Integer societeId);
    List<Diffusion> findByPubliciteId(Integer publiciteId);
    List<Diffusion> findByMoisAndAnnee(Integer mois, Integer annee);
}
