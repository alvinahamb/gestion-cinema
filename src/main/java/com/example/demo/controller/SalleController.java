package com.example.demo.controller;

import com.example.demo.dto.SalleDTO;
import com.example.demo.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/salles")
public class SalleController {
    @Autowired
    private SalleService salleService;

    @GetMapping
    public ResponseEntity<List<SalleDTO>> getAllSalles() {
        List<SalleDTO> salles = salleService.getAllSalles();
        return ResponseEntity.ok(salles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalleDTO> getSalleById(@PathVariable Integer id) {
        Optional<SalleDTO> salle = salleService.getSalleById(id);
        return salle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SalleDTO> createSalle(@RequestBody SalleDTO salleDTO) {
        SalleDTO createdSalle = salleService.createSalle(salleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalleDTO> updateSalle(@PathVariable Integer id, @RequestBody SalleDTO salleDTO) {
        SalleDTO updatedSalle = salleService.updateSalle(id, salleDTO);
        if (updatedSalle != null) {
            return ResponseEntity.ok(updatedSalle);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalle(@PathVariable Integer id) {
        if (salleService.deleteSalle(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
