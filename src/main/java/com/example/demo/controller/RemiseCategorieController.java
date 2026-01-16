package com.example.demo.controller;

import com.example.demo.dto.RemiseCategorieDTO;
import com.example.demo.dto.TypePlaceDTO;
import com.example.demo.dto.CategorieClientDTO;
import com.example.demo.service.RemiseCategorieService;
import com.example.demo.service.TypePlaceService;
import com.example.demo.service.CategorieClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/remise-categories")
public class RemiseCategorieController {
    @Autowired
    private RemiseCategorieService remiseCategorieService;

    @Autowired
    private TypePlaceService typePlaceService;

    @Autowired
    private CategorieClientService categorieCategoryService;

    @GetMapping
    public String listRemiseCategories(Model model) {
        List<RemiseCategorieDTO> remiseCategories = remiseCategorieService.getAllRemiseCategories();
        model.addAttribute("remiseCategories", remiseCategories);
        return "remise-categorie/list";
    }

    @GetMapping("/nouveau")
    public String newRemiseCategorie(Model model) {
        model.addAttribute("remiseCategorie", null);
        List<TypePlaceDTO> typePlaces = typePlaceService.getAllTypePlaces();
        List<CategorieClientDTO> categorieClients = categorieCategoryService.getAllCategorieClients();
        model.addAttribute("typePlaces", typePlaces);
        model.addAttribute("categorieClients", categorieClients);
        return "remise-categorie/form";
    }

    @GetMapping("/edit/{id}")
    public String editRemiseCategorie(@PathVariable Integer id, Model model) {
        Optional<RemiseCategorieDTO> remiseCategorie = remiseCategorieService.getRemiseCategorieById(id);
        if (remiseCategorie.isPresent()) {
            model.addAttribute("remiseCategorie", remiseCategorie.get());
            List<TypePlaceDTO> typePlaces = typePlaceService.getAllTypePlaces();
            List<CategorieClientDTO> categorieClients = categorieCategoryService.getAllCategorieClients();
            model.addAttribute("typePlaces", typePlaces);
            model.addAttribute("categorieClients", categorieClients);
            return "remise-categorie/form";
        }
        return "redirect:/remise-categories";
    }

    @PostMapping("/save")
    public String saveRemiseCategorie(@ModelAttribute RemiseCategorieDTO remiseCategorieDTO) {
        remiseCategorieService.createRemiseCategorie(remiseCategorieDTO);
        return "redirect:/remise-categories";
    }

    @PostMapping("/update")
    public String updateRemiseCategorie(@RequestParam Integer id, @ModelAttribute RemiseCategorieDTO remiseCategorieDTO) {
        remiseCategorieService.updateRemiseCategorie(id, remiseCategorieDTO);
        return "redirect:/remise-categories";
    }

    @PostMapping("/delete/{id}")
    public String deleteRemiseCategorie(@PathVariable Integer id) {
        remiseCategorieService.deleteRemiseCategorie(id);
        return "redirect:/remise-categories";
    }
}
