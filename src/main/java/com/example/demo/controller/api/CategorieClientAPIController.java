package com.example.demo.controller.api;

import com.example.demo.dto.CategorieClientDTO;
import com.example.demo.service.CategorieClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorie-clients")
public class CategorieClientAPIController {
    @Autowired
    private CategorieClientService categorieClientService;

    @GetMapping
    public ResponseEntity<List<CategorieClientDTO>> getAllCategorieClients() {
        List<CategorieClientDTO> categorieClients = categorieClientService.getAllCategorieClients();
        return ResponseEntity.ok(categorieClients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorieClientDTO> getCategorieClientById(@PathVariable Integer id) {
        Optional<CategorieClientDTO> categorieClient = categorieClientService.getCategorieClientById(id);
        return categorieClient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategorieClientDTO> createCategorieClient(@RequestBody CategorieClientDTO categorieClientDTO) {
        CategorieClientDTO createdCategorieClient = categorieClientService.createCategorieClient(categorieClientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategorieClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategorieClientDTO> updateCategorieClient(@PathVariable Integer id, @RequestBody CategorieClientDTO categorieClientDTO) {
        CategorieClientDTO updatedCategorieClient = categorieClientService.updateCategorieClient(id, categorieClientDTO);
        if (updatedCategorieClient != null) {
            return ResponseEntity.ok(updatedCategorieClient);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorieClient(@PathVariable Integer id) {
        boolean deleted = categorieClientService.deleteCategorieClient(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}