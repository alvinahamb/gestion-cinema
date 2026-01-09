package com.example.demo.controller.api;

import com.example.demo.dto.LangueDTO;
import com.example.demo.service.LangueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/langues")
public class LangueAPIController {
    @Autowired
    private LangueService languageService;

    @GetMapping
    public ResponseEntity<List<LangueDTO>> getAllLangues() {
        List<LangueDTO> langues = languageService.getAllLangues();
        return ResponseEntity.ok(langues);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LangueDTO> getLangueById(@PathVariable Integer id) {
        Optional<LangueDTO> langue = languageService.getLangueById(id);
        return langue.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LangueDTO> createLangue(@RequestBody LangueDTO languageDTO) {
        LangueDTO createdLanguage = languageService.createLangue(languageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLanguage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LangueDTO> updateLangue(@PathVariable Integer id, @RequestBody LangueDTO languageDTO) {
        LangueDTO updatedLanguage = languageService.updateLangue(id, languageDTO);
        if (updatedLanguage != null) {
            return ResponseEntity.ok(updatedLanguage);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLangue(@PathVariable Integer id) {
        if (languageService.deleteLangue(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
