package com.example.demo.controller;

import com.example.demo.dto.SeanceDTO;
import com.example.demo.service.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seances")
public class SeanceController {
    @Autowired
    private SeanceService seanceService;

    @GetMapping
    public ResponseEntity<List<SeanceDTO>> getAllSeances() {
        List<SeanceDTO> seances = seanceService.getAllSeances();
        return ResponseEntity.ok(seances);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeanceDTO> getSeanceById(@PathVariable Integer id) {
        Optional<SeanceDTO> seance = seanceService.getSeanceById(id);
        return seance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SeanceDTO> createSeance(@RequestBody SeanceDTO seanceDTO) {
        SeanceDTO createdSeance = seanceService.createSeance(seanceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSeance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeanceDTO> updateSeance(@PathVariable Integer id, @RequestBody SeanceDTO seanceDTO) {
        SeanceDTO updatedSeance = seanceService.updateSeance(id, seanceDTO);
        if (updatedSeance != null) {
            return ResponseEntity.ok(updatedSeance);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeance(@PathVariable Integer id) {
        if (seanceService.deleteSeance(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
