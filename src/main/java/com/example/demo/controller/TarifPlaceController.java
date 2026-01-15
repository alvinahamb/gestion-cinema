package com.example.demo.controller;

import com.example.demo.dto.TarifPlaceDTO;
import com.example.demo.dto.TypePlaceDTO;
import com.example.demo.service.TarifPlaceService;
import com.example.demo.service.TypePlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tarif-places")
public class TarifPlaceController {
    @Autowired
    private TarifPlaceService tarifPlaceService;

    @Autowired
    private TypePlaceService typePlaceService;

    @GetMapping
    public String listTarifPlaces(Model model) {
        List<TarifPlaceDTO> tarifPlaces = tarifPlaceService.getAllTarifPlaces();
        model.addAttribute("tarifPlaces", tarifPlaces);
        return "tarif-place/list";
    }

    @GetMapping("/nouveau")
    public String newTarifPlace(Model model) {
        model.addAttribute("tarifPlace", null);
        List<TypePlaceDTO> typePlaces = typePlaceService.getAllTypePlaces();
        model.addAttribute("typePlaces", typePlaces);
        return "tarif-place/form";
    }

    @GetMapping("/edit/{id}")
    public String editTarifPlace(@PathVariable Integer id, Model model) {
        Optional<TarifPlaceDTO> tarifPlace = tarifPlaceService.getTarifPlaceById(id);
        if (tarifPlace.isPresent()) {
            model.addAttribute("tarifPlace", tarifPlace.get());
            List<TypePlaceDTO> typePlaces = typePlaceService.getAllTypePlaces();
            model.addAttribute("typePlaces", typePlaces);
            return "tarif-place/form";
        }
        return "redirect:/tarif-places";
    }

    @PostMapping("/save")
    public String saveTarifPlace(@ModelAttribute TarifPlaceDTO tarifPlaceDTO) {
        tarifPlaceService.createTarifPlace(tarifPlaceDTO);
        return "redirect:/tarif-places";
    }

    @PostMapping("/update")
    public String updateTarifPlace(@RequestParam Integer id, @ModelAttribute TarifPlaceDTO tarifPlaceDTO) {
        tarifPlaceService.updateTarifPlace(id, tarifPlaceDTO);
        return "redirect:/tarif-places";
    }

    @PostMapping("/delete/{id}")
    public String deleteTarifPlace(@PathVariable Integer id) {
        tarifPlaceService.deleteTarifPlace(id);
        return "redirect:/tarif-places";
    }
}