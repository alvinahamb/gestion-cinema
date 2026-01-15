package com.example.demo.controller.api;

import com.example.demo.dto.PaiementDTO;
import com.example.demo.service.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paiement")
public class PaiementAPIController {
    @Autowired
    private PaiementService paiementService;

    @GetMapping
    public ResponseEntity<List<PaiementDTO>> getAllPaiements() {
        List<PaiementDTO> paiements = paiementService.getAllPaiements();
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaiementDTO> getPaiementById(@PathVariable Integer id) {
        Optional<PaiementDTO> paiement = paiementService.getPaiementById(id);
        return paiement.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PaiementDTO> createPaiement(@RequestBody PaiementDTO paiementDTO) {
        PaiementDTO createdPaiement = paiementService.createPaiement(paiementDTO);
        return ResponseEntity.ok(createdPaiement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaiementDTO> updatePaiement(@PathVariable Integer id, @RequestBody PaiementDTO paiementDTO) {
        PaiementDTO updatedPaiement = paiementService.updatePaiement(id, paiementDTO);
        if (updatedPaiement != null) {
            return ResponseEntity.ok(updatedPaiement);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable Integer id) {
        boolean deleted = paiementService.deletePaiement(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
