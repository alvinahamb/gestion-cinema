package com.example.demo.controller;

import com.example.demo.dto.MethodePaiementDTO;
import com.example.demo.service.MethodePaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/methodes-paiement")
public class MethodePaiementController {
    @Autowired
    private MethodePaiementService methodePaiementService;

    @GetMapping
    public ResponseEntity<List<MethodePaiementDTO>> getAllMethodesPaiement() {
        List<MethodePaiementDTO> methodesPaiement = methodePaiementService.getAllMethodesPaiement();
        return ResponseEntity.ok(methodesPaiement);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MethodePaiementDTO> getMethodePaiementById(@PathVariable Integer id) {
        Optional<MethodePaiementDTO> methodePaiement = methodePaiementService.getMethodePaiementById(id);
        return methodePaiement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MethodePaiementDTO> createMethodePaiement(@RequestBody MethodePaiementDTO methodePaiementDTO) {
        MethodePaiementDTO createdMethodePaiement = methodePaiementService.createMethodePaiement(methodePaiementDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMethodePaiement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MethodePaiementDTO> updateMethodePaiement(@PathVariable Integer id, @RequestBody MethodePaiementDTO methodePaiementDTO) {
        MethodePaiementDTO updatedMethodePaiement = methodePaiementService.updateMethodePaiement(id, methodePaiementDTO);
        if (updatedMethodePaiement != null) {
            return ResponseEntity.ok(updatedMethodePaiement);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMethodePaiement(@PathVariable Integer id) {
        if (methodePaiementService.deleteMethodePaiement(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
