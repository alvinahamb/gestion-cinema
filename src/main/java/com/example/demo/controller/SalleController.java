package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.*;
import com.example.demo.controller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/salles")
public class SalleController {
    @Autowired
    private SalleService salleService;
    @Autowired
    private TypePlaceService typePlaceService;
    @Autowired
    private PlaceService placeService;

    @GetMapping
    public String listSalles(Model model) {
        List<SalleDTO> salles = salleService.getAllSalles();
        model.addAttribute("salles", salles);
        return "salle/list";
    }

    @GetMapping("/nouveau")
    public String newSalle(Model model) {
        List<TypePlaceDTO> typePlaces = typePlaceService.getAllTypePlaces();
        model.addAttribute("typePlaces", typePlaces);
        model.addAttribute("salle", null);
        return "salle/form";
    }

    @GetMapping("/edit/{id}")
    public String editSalle(@PathVariable Integer id, Model model) {
        Optional<SalleDTO> salle = salleService.getSalleById(id);
        if (salle.isPresent()) {
            model.addAttribute("salle", salle.get());
            return "salle/form";
        }
        return "redirect:/salles";
    }

    @PostMapping("/save")
    public String saveSalle(@ModelAttribute SalleDTO salleDTO, int[] typePlaces, int[] nombrePlaces) {
        SalleDTO savedSalle = salleService.createSalle(salleDTO);
        // Créer les places
        int currentPlace = 0;
        for (int i = 0; i < typePlaces.length; i++) {
            int typeId = typePlaces[i];
            int count = nombrePlaces[i];
            TypePlaceDTO typePlaceDTO = typePlaceService.getTypePlaceById(typeId).orElseThrow();
            for (int j = 0; j < count; j++) {
                int ligne = (currentPlace / salleDTO.getColonne()) + 1;
                int colonne = (currentPlace % salleDTO.getColonne()) + 1;
                PlaceDTO placeDTO = new PlaceDTO();
                placeDTO.setIdSalle(savedSalle.getIdSalle());
                placeDTO.setIdTypePlace(typePlaceDTO.getIdTypePlace());
                placeDTO.setNumeroLigne(ligne);
                placeDTO.setNumeroColonne(colonne);
                placeService.createPlace(placeDTO);
                currentPlace++;
            }
        }
        return "redirect:/salles";
    }

    @PostMapping("/update")
    public String updateSalle(@RequestParam Integer id, @ModelAttribute SalleDTO salleDTO) {
        salleService.updateSalle(id, salleDTO);
        return "redirect:/salles";
    }

    @PostMapping("/delete/{id}")
    public String deleteSalle(@PathVariable Integer id) {
        salleService.deleteSalle(id);
        return "redirect:/salles";
    }
}
