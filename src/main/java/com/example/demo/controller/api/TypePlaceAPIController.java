package com.example.demo.controller.api;

import com.example.demo.dto.TypePlaceDTO;
import com.example.demo.service.TypePlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/type-place")
public class TypePlaceAPIController {
    @Autowired
    private TypePlaceService typePlaceService;

    @GetMapping
    public ResponseEntity<List<TypePlaceDTO>> getAllTypePlaces() {
        List<TypePlaceDTO> typePlaces = typePlaceService.getAllTypePlaces();
        return ResponseEntity.ok(typePlaces);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypePlaceDTO> getTypePlaceById(@PathVariable Integer id) {
        Optional<TypePlaceDTO> typePlace = typePlaceService.getTypePlaceById(id);
        return typePlace.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TypePlaceDTO> createTypePlace(@RequestBody TypePlaceDTO typePlaceDTO) {
        TypePlaceDTO createdTypePlace = typePlaceService.createTypePlace(typePlaceDTO);
        return ResponseEntity.ok(createdTypePlace);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypePlaceDTO> updateTypePlace(@PathVariable Integer id, @RequestBody TypePlaceDTO typePlaceDTO) {
        TypePlaceDTO updatedTypePlace = typePlaceService.updateTypePlace(id, typePlaceDTO);
        if (updatedTypePlace != null) {
            return ResponseEntity.ok(updatedTypePlace);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypePlace(@PathVariable Integer id) {
        boolean deleted = typePlaceService.deleteTypePlace(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
