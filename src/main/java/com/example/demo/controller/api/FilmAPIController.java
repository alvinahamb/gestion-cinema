package com.example.demo.controller.api;

import com.example.demo.dto.FilmDTO;
import com.example.demo.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/films")
public class FilmAPIController {
    @Autowired
    private FilmService filmService;

    @GetMapping
    public ResponseEntity<List<FilmDTO>> getAllFilms() {
        List<FilmDTO> films = filmService.getAllFilms();
        return ResponseEntity.ok(films);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDTO> getFilmById(@PathVariable Integer id) {
        Optional<FilmDTO> film = filmService.getFilmById(id);
        return film.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FilmDTO> createFilm(@RequestBody FilmDTO filmDTO) {
        FilmDTO createdFilm = filmService.createFilm(filmDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFilm);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmDTO> updateFilm(@PathVariable Integer id, @RequestBody FilmDTO filmDTO) {
        FilmDTO updatedFilm = filmService.updateFilm(id, filmDTO);
        if (updatedFilm != null) {
            return ResponseEntity.ok(updatedFilm);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Integer id) {
        if (filmService.deleteFilm(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
