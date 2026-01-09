package com.example.demo.controller;

import com.example.demo.dto.GenreDTO;
import com.example.demo.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping
    public String listGenres(Model model) {
        List<GenreDTO> genres = genreService.getAllGenres();
        model.addAttribute("genres", genres);
        return "genre/list";
    }

    @GetMapping("/nouveau")
    public String newGenre(Model model) {
        model.addAttribute("genre", null);
        return "genre/form";
    }

    @GetMapping("/edit/{id}")
    public String editGenre(@PathVariable Integer id, Model model) {
        Optional<GenreDTO> genre = genreService.getGenreById(id);
        if (genre.isPresent()) {
            model.addAttribute("genre", genre.get());
            return "genre/form";
        }
        return "redirect:/genres";
    }

    @PostMapping("/save")
    public String saveGenre(@ModelAttribute GenreDTO genreDTO) {
        genreService.createGenre(genreDTO);
        return "redirect:/genres";
    }

    @PostMapping("/update")
    public String updateGenre(@RequestParam Integer id, @ModelAttribute GenreDTO genreDTO) {
        genreService.updateGenre(id, genreDTO);
        return "redirect:/genres";
    }

    @PostMapping("/delete/{id}")
    public String deleteGenre(@PathVariable Integer id) {
        genreService.deleteGenre(id);
        return "redirect:/genres";
    }
}
