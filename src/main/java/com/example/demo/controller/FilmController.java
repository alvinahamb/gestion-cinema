package com.example.demo.controller;

import com.example.demo.dto.FilmDTO;
import com.example.demo.dto.LangueDTO;
import com.example.demo.dto.PaysDTO;
import com.example.demo.service.FilmService;
import com.example.demo.service.LangueService;
import com.example.demo.service.PaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/films")
public class FilmController {
    @Autowired
    private FilmService filmService;
    
    @Autowired
    private LangueService languageService;
    
    @Autowired
    private PaysService paysService;

    @GetMapping
    public String listFilms(Model model) {
        List<FilmDTO> films = filmService.getAllFilms();
        model.addAttribute("films", films);
        return "film/list";
    }

    @GetMapping("/nouveau")
    public String newFilm(Model model) {
        model.addAttribute("film", null);
        model.addAttribute("langues", languageService.getAllLangues());
        model.addAttribute("pays", paysService.getAllPays());
        return "film/form";
    }

    @GetMapping("/edit/{id}")
    public String editFilm(@PathVariable Integer id, Model model) {
        Optional<FilmDTO> film = filmService.getFilmById(id);
        if (film.isPresent()) {
            model.addAttribute("film", film.get());
            model.addAttribute("langues", languageService.getAllLangues());
            model.addAttribute("pays", paysService.getAllPays());
            return "film/form";
        }
        return "redirect:/films";
    }

    @PostMapping("/save")
    public String saveFilm(@ModelAttribute FilmDTO filmDTO) {
        filmService.createFilm(filmDTO);
        return "redirect:/films";
    }

    @PostMapping("/update")
    public String updateFilm(@RequestParam Integer id, @ModelAttribute FilmDTO filmDTO) {
        filmService.updateFilm(id, filmDTO);
        return "redirect:/films";
    }

    @PostMapping("/delete/{id}")
    public String deleteFilm(@PathVariable Integer id) {
        filmService.deleteFilm(id);
        return "redirect:/films";
    }
}
