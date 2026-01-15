package com.example.demo.controller;

import com.example.demo.dto.TarifSeancePlaceDTO;
import com.example.demo.service.TarifSeancePlaceService;
import com.example.demo.service.SeanceService;
import com.example.demo.service.CategorieClientService;
import com.example.demo.service.TypePlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tarif-seance-places")
public class TarifSeancePlaceController {
    @Autowired
    private TarifSeancePlaceService tarifSeancePlaceService;
    
    @Autowired
    private SeanceService seanceService;
    
    @Autowired
    private CategorieClientService categorieClientService;
    
    @Autowired
    private TypePlaceService typePlaceService;

    @GetMapping
    public String listTarifSeancePlaces(Model model) {
        List<TarifSeancePlaceDTO> tarifSeancePlaces = tarifSeancePlaceService.getAllTarifSeancePlaces();
        model.addAttribute("tarifSeancePlaces", tarifSeancePlaces);
        return "tarif-seance-place/list";
    }

    @GetMapping("/nouveau")
    public String newTarifSeancePlace(Model model) {
        model.addAttribute("tarifSeancePlace", null);
        model.addAttribute("seances", seanceService.getAllSeances());
        model.addAttribute("categoriesClient", categorieClientService.getAllCategorieClients());
        model.addAttribute("typePlaces", typePlaceService.getAllTypePlaces());
        return "tarif-seance-place/form";
    }

    @GetMapping("/edit/{id}")
    public String editTarifSeancePlace(@PathVariable Integer id, Model model) {
        Optional<TarifSeancePlaceDTO> tarifSeancePlace = tarifSeancePlaceService.getTarifSeancePlaceById(id);
        if (tarifSeancePlace.isPresent()) {
            model.addAttribute("tarifSeancePlace", tarifSeancePlace.get());
            model.addAttribute("seances", seanceService.getAllSeances());
            model.addAttribute("categoriesClient", categorieClientService.getAllCategorieClients());
            model.addAttribute("typePlaces", typePlaceService.getAllTypePlaces());
            return "tarif-seance-place/form";
        }
        return "redirect:/tarif-seance-places";
    }

    @PostMapping("/save")
    public String saveTarifSeancePlace(@ModelAttribute TarifSeancePlaceDTO dto) {
        tarifSeancePlaceService.createTarifSeancePlace(dto);
        return "redirect:/tarif-seance-places";
    }

    @PostMapping("/update")
    public String updateTarifSeancePlace(@RequestParam Integer id, @ModelAttribute TarifSeancePlaceDTO dto) {
        tarifSeancePlaceService.updateTarifSeancePlace(id, dto);
        return "redirect:/tarif-seance-places";
    }

    @PostMapping("/delete/{id}")
    public String deleteTarifSeancePlace(@PathVariable Integer id) {
        tarifSeancePlaceService.deleteTarifSeancePlace(id);
        return "redirect:/tarif-seance-places";
    }
}
