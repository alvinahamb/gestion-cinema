package com.example.demo.controller.api;

import com.example.demo.dto.PaysDTO;
import com.example.demo.service.PaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pays")
public class PaysAPIController {
    @Autowired
    private PaysService paysService;

    @GetMapping
    public ResponseEntity<List<PaysDTO>> getAllPays() {
        List<PaysDTO> pays = paysService.getAllPays();
        return ResponseEntity.ok(pays);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaysDTO> getPaysById(@PathVariable Integer id) {
        Optional<PaysDTO> pays = paysService.getPaysById(id);
        return pays.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PaysDTO> createPays(@RequestBody PaysDTO paysDTO) {
        PaysDTO createdPays = paysService.createPays(paysDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPays);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaysDTO> updatePays(@PathVariable Integer id, @RequestBody PaysDTO paysDTO) {
        PaysDTO updatedPays = paysService.updatePays(id, paysDTO);
        if (updatedPays != null) {
            return ResponseEntity.ok(updatedPays);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePays(@PathVariable Integer id) {
        if (paysService.deletePays(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
