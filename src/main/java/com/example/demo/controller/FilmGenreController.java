package com.example.demo.controller;

import com.example.demo.dto.FilmGenreDTO;
import com.example.demo.service.FilmGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/film-genres")
public class FilmGenreController {
    @Autowired
    private FilmGenreService filmGenreService;

    @GetMapping
    public String listFilmGenres(Model model) {
        List<FilmGenreDTO> filmGenres = filmGenreService.getAllFilmGenres();
        model.addAttribute("filmGenres", filmGenres);
        return "film-genre/list";
    }

    @GetMapping("/nouveau")
    public String newFilmGenre(Model model) {
        model.addAttribute("filmGenre", null);
        return "film-genre/form";
    }

    @GetMapping("/{id}/modifier")
    public String editFilmGenre(@PathVariable Integer id, Model model) {
        Optional<FilmGenreDTO> filmGenre = filmGenreService.getFilmGenreById(id);
        if (filmGenre.isPresent()) {
            model.addAttribute("filmGenre", filmGenre.get());
            return "film-genre/form";
        }
        return "redirect:/film-genres";
    }

    @PostMapping
    public String saveFilmGenre(@ModelAttribute FilmGenreDTO filmGenreDTO) {
        if (filmGenreDTO.getId() != null) {
            filmGenreService.updateFilmGenre(filmGenreDTO.getId(), filmGenreDTO);
        } else {
            filmGenreService.createFilmGenre(filmGenreDTO);
        }
        return "redirect:/film-genres";
    }

    @GetMapping("/{id}/supprimer")
    public String deleteFilmGenre(@PathVariable Integer id) {
        filmGenreService.deleteFilmGenre(id);
        return "redirect:/film-genres";
    }
}