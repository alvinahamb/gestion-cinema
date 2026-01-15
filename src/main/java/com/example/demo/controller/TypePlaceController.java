package com.example.demo.controller;

import com.example.demo.dto.TypePlaceDTO;
import com.example.demo.service.TypePlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/types-places")
public class TypePlaceController {
    @Autowired
    private TypePlaceService typePlaceService;

    @GetMapping
    public String listTypePlaces(Model model) {
        List<TypePlaceDTO> typePlaces = typePlaceService.getAllTypePlaces();
        model.addAttribute("typePlaces", typePlaces);
        return "type-place/list";
    }

    @GetMapping("/nouveau")
    public String newTypePlace(Model model) {
        model.addAttribute("typePlace", null);
        return "type-place/form";
    }

    @GetMapping("/edit/{id}")
    public String editTypePlace(@PathVariable Integer id, Model model) {
        Optional<TypePlaceDTO> typePlace = typePlaceService.getTypePlaceById(id);
        if (typePlace.isPresent()) {
            model.addAttribute("typePlace", typePlace.get());
            return "type-place/form";
        }
        return "redirect:/types-places";
    }

    @PostMapping("/save")
    public String saveTypePlace(@ModelAttribute TypePlaceDTO typePlaceDTO) {
        if (typePlaceDTO.getIdTypePlace() != null) {
            typePlaceService.updateTypePlace(typePlaceDTO.getIdTypePlace(), typePlaceDTO);
        } else {
            typePlaceService.createTypePlace(typePlaceDTO);
        }
        return "redirect:/types-places";
    }

    @PostMapping("/delete/{id}")
    public String deleteTypePlace(@PathVariable Integer id) {
        typePlaceService.deleteTypePlace(id);
        return "redirect:/types-places";
    }
}
