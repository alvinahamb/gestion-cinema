package com.example.demo.controller.api;

import com.example.demo.dto.TypeStatutDTO;
import com.example.demo.service.TypeStatutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/type-statuts")
public class TypeStatutAPIController {
    @Autowired
    private TypeStatutService typeStatutService;

    @GetMapping
    public ResponseEntity<List<TypeStatutDTO>> getAllTypeStatuts() {
        List<TypeStatutDTO> typeStatuts = typeStatutService.getAllTypeStatuts();
        return ResponseEntity.ok(typeStatuts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeStatutDTO> getTypeStatutById(@PathVariable Integer id) {
        Optional<TypeStatutDTO> typeStatut = typeStatutService.getTypeStatutById(id);
        return typeStatut.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TypeStatutDTO> createTypeStatut(@RequestBody TypeStatutDTO typeStatutDTO) {
        TypeStatutDTO createdTypeStatut = typeStatutService.createTypeStatut(typeStatutDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTypeStatut);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeStatutDTO> updateTypeStatut(@PathVariable Integer id, @RequestBody TypeStatutDTO typeStatutDTO) {
        TypeStatutDTO updatedTypeStatut = typeStatutService.updateTypeStatut(id, typeStatutDTO);
        if (updatedTypeStatut != null) {
            return ResponseEntity.ok(updatedTypeStatut);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeStatut(@PathVariable Integer id) {
        boolean deleted = typeStatutService.deleteTypeStatut(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}