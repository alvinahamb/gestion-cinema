package com.example.demo.controller.api;

import com.example.demo.dto.FilmGenreDTO;
import com.example.demo.service.FilmGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/film-genres")
public class FilmGenreAPIController {
    @Autowired
    private FilmGenreService filmGenreService;

    @GetMapping
    public ResponseEntity<List<FilmGenreDTO>> getAllFilmGenres() {
        List<FilmGenreDTO> filmGenres = filmGenreService.getAllFilmGenres();
        return ResponseEntity.ok(filmGenres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmGenreDTO> getFilmGenreById(@PathVariable Integer id) {
        Optional<FilmGenreDTO> filmGenre = filmGenreService.getFilmGenreById(id);
        return filmGenre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FilmGenreDTO> createFilmGenre(@RequestBody FilmGenreDTO filmGenreDTO) {
        FilmGenreDTO createdFilmGenre = filmGenreService.createFilmGenre(filmGenreDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFilmGenre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmGenreDTO> updateFilmGenre(@PathVariable Integer id, @RequestBody FilmGenreDTO filmGenreDTO) {
        FilmGenreDTO updatedFilmGenre = filmGenreService.updateFilmGenre(id, filmGenreDTO);
        if (updatedFilmGenre != null) {
            return ResponseEntity.ok(updatedFilmGenre);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilmGenre(@PathVariable Integer id) {
        boolean deleted = filmGenreService.deleteFilmGenre(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}