package com.example.demo.controller;

import com.example.demo.dto.FilmVersionDTO;
import com.example.demo.service.FilmVersionService;
import com.example.demo.service.FilmService;
import com.example.demo.service.LangueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/film-versions")
public class FilmVersionController {
    @Autowired
    private FilmVersionService filmVersionService;
    
    @Autowired
    private FilmService filmService;
    
    @Autowired
    private LangueService langueService;

    @GetMapping
    public String listFilmVersions(Model model) {
        List<FilmVersionDTO> filmVersions = filmVersionService.getAllFilmVersions();
        model.addAttribute("filmVersions", filmVersions);
        return "film-version/list";
    }

    @GetMapping("/nouveau")
    public String newFilmVersion(Model model) {
        model.addAttribute("filmVersion", null);
        model.addAttribute("films", filmService.getAllFilms());
        model.addAttribute("langues", langueService.getAllLangues());
        return "film-version/form";
    }

    @GetMapping("/edit/{id}")
    public String editFilmVersion(@PathVariable Integer id, Model model) {
        Optional<FilmVersionDTO> filmVersion = filmVersionService.getFilmVersionById(id);
        if (filmVersion.isPresent()) {
            model.addAttribute("filmVersion", filmVersion.get());
            model.addAttribute("films", filmService.getAllFilms());
            model.addAttribute("langues", langueService.getAllLangues());
            return "film-version/form";
        }
        return "redirect:/film-versions";
    }

    @PostMapping("/save")
    public String saveFilmVersion(@ModelAttribute FilmVersionDTO filmVersionDTO) {
        filmVersionService.createFilmVersion(filmVersionDTO);
        return "redirect:/film-versions";
    }

    @PostMapping("/update")
    public String updateFilmVersion(@RequestParam Integer id, @ModelAttribute FilmVersionDTO filmVersionDTO) {
        filmVersionService.updateFilmVersion(id, filmVersionDTO);
        return "redirect:/film-versions";
    }

    @PostMapping("/delete/{id}")
    public String deleteFilmVersion(@PathVariable Integer id) {
        filmVersionService.deleteFilmVersion(id);
        return "redirect:/film-versions";
    }
}
