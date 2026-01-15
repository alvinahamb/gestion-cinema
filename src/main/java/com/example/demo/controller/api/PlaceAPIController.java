package com.example.demo.controller.api;

import com.example.demo.dto.PlaceDTO;
import com.example.demo.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/place")
public class PlaceAPIController {
    @Autowired
    private PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<PlaceDTO>> getAllPlaces() {
        List<PlaceDTO> places = placeService.getAllPlaces();
        return ResponseEntity.ok(places);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDTO> getPlaceById(@PathVariable Integer id) {
        Optional<PlaceDTO> place = placeService.getPlaceById(id);
        return place.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PlaceDTO> createPlace(@RequestBody PlaceDTO placeDTO) {
        PlaceDTO createdPlace = placeService.createPlace(placeDTO);
        return ResponseEntity.ok(createdPlace);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaceDTO> updatePlace(@PathVariable Integer id, @RequestBody PlaceDTO placeDTO) {
        PlaceDTO updatedPlace = placeService.updatePlace(id, placeDTO);
        if (updatedPlace != null) {
            return ResponseEntity.ok(updatedPlace);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable Integer id) {
        boolean deleted = placeService.deletePlace(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
