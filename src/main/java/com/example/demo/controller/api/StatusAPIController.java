package com.example.demo.controller.api;

import com.example.demo.dto.StatusDTO;
import com.example.demo.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/status")
public class StatusAPIController {
    @Autowired
    private StatusService statusService;

    @GetMapping
    public ResponseEntity<List<StatusDTO>> getAllStatus() {
        List<StatusDTO> statusList = statusService.getAllStatus();
        return ResponseEntity.ok(statusList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusDTO> getStatusById(@PathVariable Integer id) {
        Optional<StatusDTO> status = statusService.getStatusById(id);
        return status.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StatusDTO> createStatus(@RequestBody StatusDTO statusDTO) {
        StatusDTO createdStatus = statusService.createStatus(statusDTO);
        return ResponseEntity.ok(createdStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusDTO> updateStatus(@PathVariable Integer id, @RequestBody StatusDTO statusDTO) {
        StatusDTO updatedStatus = statusService.updateStatus(id, statusDTO);
        if (updatedStatus != null) {
            return ResponseEntity.ok(updatedStatus);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Integer id) {
        boolean deleted = statusService.deleteStatus(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
