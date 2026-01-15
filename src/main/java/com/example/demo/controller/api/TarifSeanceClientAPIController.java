package com.example.demo.controller.api;

import com.example.demo.dto.TarifSeanceClientDTO;
import com.example.demo.service.TarifSeanceClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarif-seance-clients")
public class TarifSeanceClientAPIController {
    @Autowired
    private TarifSeanceClientService tarifSeanceClientService;

    @GetMapping
    public ResponseEntity<List<TarifSeanceClientDTO>> getAllTarifSeanceClients() {
        List<TarifSeanceClientDTO> tarifSeanceClients = tarifSeanceClientService.getAllTarifSeanceClients();
        return ResponseEntity.ok(tarifSeanceClients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarifSeanceClientDTO> getTarifSeanceClientById(@PathVariable Integer id) {
        Optional<TarifSeanceClientDTO> tarifSeanceClient = tarifSeanceClientService.getTarifSeanceClientById(id);
        return tarifSeanceClient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TarifSeanceClientDTO> createTarifSeanceClient(@RequestBody TarifSeanceClientDTO tarifSeanceClientDTO) {
        TarifSeanceClientDTO createdTarifSeanceClient = tarifSeanceClientService.createTarifSeanceClient(tarifSeanceClientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTarifSeanceClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarifSeanceClientDTO> updateTarifSeanceClient(@PathVariable Integer id, @RequestBody TarifSeanceClientDTO tarifSeanceClientDTO) {
        TarifSeanceClientDTO updatedTarifSeanceClient = tarifSeanceClientService.updateTarifSeanceClient(id, tarifSeanceClientDTO);
        if (updatedTarifSeanceClient != null) {
            return ResponseEntity.ok(updatedTarifSeanceClient);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarifSeanceClient(@PathVariable Integer id) {
        boolean deleted = tarifSeanceClientService.deleteTarifSeanceClient(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}