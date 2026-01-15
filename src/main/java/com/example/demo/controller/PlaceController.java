package com.example.demo.controller;

import com.example.demo.dto.PlaceDTO;
import com.example.demo.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/place")
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    @GetMapping
    public String listPlaces(Model model) {
        List<PlaceDTO> places = placeService.getAllPlaces();
        model.addAttribute("places", places);
        return "place/list";
    }

    @GetMapping("/form")
    public String showForm(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            Optional<PlaceDTO> place = placeService.getPlaceById(id);
            place.ifPresent(p -> model.addAttribute("place", p));
        } else {
            model.addAttribute("place", new PlaceDTO());
        }
        return "place/form";
    }

    @PostMapping("/save")
    public String savePlace(@ModelAttribute PlaceDTO placeDTO) {
        placeService.createPlace(placeDTO);
        return "redirect:/place";
    }

    @PostMapping("/update/{id}")
    public String updatePlace(@PathVariable Integer id, @ModelAttribute PlaceDTO placeDTO) {
        placeService.updatePlace(id, placeDTO);
        return "redirect:/place";
    }

    @GetMapping("/delete/{id}")
    public String deletePlace(@PathVariable Integer id) {
        placeService.deletePlace(id);
        return "redirect:/place";
    }
}
