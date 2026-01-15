package com.example.demo.controller;

import com.example.demo.dto.FilmDTO;
import com.example.demo.dto.FilmVersionDTO;
import com.example.demo.dto.LangueDTO;
import com.example.demo.service.FilmService;
import com.example.demo.service.FilmVersionService;
import com.example.demo.service.LangueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/films/{filmId}/versions")
public class FilmVersionManagementController {

    @Autowired
    private FilmVersionService filmVersionService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private LangueService langueService;

    @GetMapping
    public String manageVersions(@PathVariable Integer filmId, Model model) {
        FilmDTO film = filmService.getFilmById(filmId).orElse(null);
        if (film == null) {
            return "redirect:/films";
        }

        // Get all languages
        List<LangueDTO> allLangues = langueService.getAllLangues();

        // Get current versions for this film
        List<FilmVersionDTO> filmVersions = filmVersionService.getFilmVersionsByFilmId(filmId);

        List<LangueDTO> currentLangues = filmVersions.stream()
                .filter(fv -> fv.getLangue() != null)
                .map(FilmVersionDTO::getLangue)
                .collect(Collectors.toList());

        // Get available languages (not already associated)
        List<LangueDTO> availableLangues = allLangues.stream()
                .filter(langue -> currentLangues.stream()
                        .noneMatch(cl -> cl.getIdLangue().equals(langue.getIdLangue())))
                .collect(Collectors.toList());

        model.addAttribute("film", film);
        model.addAttribute("currentVersions", filmVersions);
        model.addAttribute("availableLangues", availableLangues);

        return "film/manage-versions";
    }

    @PostMapping("/add")
    public String addVersion(@PathVariable Integer filmId,
                            @RequestParam Integer langueId,
                            @RequestParam(required = false) Boolean sousTitre,
                            RedirectAttributes redirectAttributes) {
        try {
            FilmVersionDTO filmVersionDTO = new FilmVersionDTO();
            filmVersionDTO.setIdFilm(filmId);
            filmVersionDTO.setIdLangue(langueId);
            filmVersionDTO.setSousTitre(sousTitre != null ? sousTitre : false);

            filmVersionService.createFilmVersion(filmVersionDTO);
            redirectAttributes.addFlashAttribute("success", "Version ajoutée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout de la version");
        }

        return "redirect:/films/" + filmId + "/versions";
    }

    @PostMapping("/remove/{versionId}")
    public String removeVersion(@PathVariable Integer filmId,
                               @PathVariable Integer versionId,
                               RedirectAttributes redirectAttributes) {
        try {
            filmVersionService.deleteFilmVersion(versionId);
            redirectAttributes.addFlashAttribute("success", "Version retirée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors du retrait de la version");
        }

        return "redirect:/films/" + filmId + "/versions";
    }
}