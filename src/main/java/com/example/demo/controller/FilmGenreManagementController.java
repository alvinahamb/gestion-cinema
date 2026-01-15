package com.example.demo.controller;

import com.example.demo.dto.FilmDTO;
import com.example.demo.dto.FilmGenreDTO;
import com.example.demo.dto.GenreDTO;
import com.example.demo.service.FilmGenreService;
import com.example.demo.service.FilmService;
import com.example.demo.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/films/{filmId}/genres")
public class FilmGenreManagementController {

    @Autowired
    private FilmGenreService filmGenreService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private GenreService genreService;

    @GetMapping
    public String manageGenres(@PathVariable Integer filmId, Model model) {
        FilmDTO film = filmService.getFilmById(filmId).orElse(null);
        if (film == null) {
            return "redirect:/films";
        }

        // Get all genres
        List<GenreDTO> allGenres = genreService.getAllGenres();

        // Get current genres for this film
        List<FilmGenreDTO> filmGenres = filmGenreService.getAllFilmGenres().stream()
                .filter(fg -> fg.getFilm() != null && fg.getFilm().getIdFilm().equals(filmId))
                .collect(Collectors.toList());

        List<GenreDTO> currentGenres = filmGenres.stream()
                .filter(fg -> fg.getGenre() != null)
                .map(FilmGenreDTO::getGenre)
                .collect(Collectors.toList());

        // Get available genres (not already associated)
        List<GenreDTO> availableGenres = allGenres.stream()
                .filter(genre -> currentGenres.stream()
                        .noneMatch(cg -> cg.getIdGenre().equals(genre.getIdGenre())))
                .collect(Collectors.toList());

        model.addAttribute("film", film);
        model.addAttribute("currentGenres", currentGenres);
        model.addAttribute("availableGenres", availableGenres);

        return "film/manage-genres";
    }

    @PostMapping("/add")
    public String addGenre(@PathVariable Integer filmId,
                          @RequestParam Integer genreId,
                          RedirectAttributes redirectAttributes) {
        try {
            FilmGenreDTO filmGenreDTO = new FilmGenreDTO();
            FilmDTO filmDTO = new FilmDTO();
            filmDTO.setIdFilm(filmId);
            filmGenreDTO.setFilm(filmDTO);

            GenreDTO genreDTO = new GenreDTO();
            genreDTO.setIdGenre(genreId);
            filmGenreDTO.setGenre(genreDTO);

            filmGenreService.createFilmGenre(filmGenreDTO);
            redirectAttributes.addFlashAttribute("success", "Genre ajouté avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout du genre");
        }

        return "redirect:/films/" + filmId + "/genres";
    }

    @PostMapping("/remove/{genreId}")
    public String removeGenre(@PathVariable Integer filmId,
                             @PathVariable Integer genreId,
                             RedirectAttributes redirectAttributes) {
        try {
            // Find and delete the FilmGenre association
            List<FilmGenreDTO> filmGenres = filmGenreService.getAllFilmGenres().stream()
                    .filter(fg -> fg.getFilm() != null && fg.getFilm().getIdFilm().equals(filmId)
                            && fg.getGenre() != null && fg.getGenre().getIdGenre().equals(genreId))
                    .collect(Collectors.toList());

            for (FilmGenreDTO fg : filmGenres) {
                filmGenreService.deleteFilmGenre(fg.getId());
            }

            redirectAttributes.addFlashAttribute("success", "Genre retiré avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors du retrait du genre");
        }

        return "redirect:/films/" + filmId + "/genres";
    }
}