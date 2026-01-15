package com.example.demo.controller;

import com.example.demo.dto.CategorieClientDTO;
import com.example.demo.service.CategorieClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categorie-clients")
public class CategorieClientController {
    @Autowired
    private CategorieClientService categorieClientService;

    @GetMapping
    public String listCategorieClients(Model model) {
        List<CategorieClientDTO> categorieClients = categorieClientService.getAllCategorieClients();
        model.addAttribute("categorieClients", categorieClients);
        return "categorie-client/list";
    }

    @GetMapping("/nouveau")
    public String newCategorieClient(Model model) {
        model.addAttribute("categorieClient", null);
        return "categorie-client/form";
    }

    @GetMapping("/edit/{id}")
    public String editCategorieClient(@PathVariable Integer id, Model model) {
        Optional<CategorieClientDTO> categorieClient = categorieClientService.getCategorieClientById(id);
        if (categorieClient.isPresent()) {
            model.addAttribute("categorieClient", categorieClient.get());
            return "categorie-client/form";
        }
        return "redirect:/categorie-clients";
    }

    @PostMapping("/save")
    public String saveCategorieClient(@ModelAttribute CategorieClientDTO categorieClientDTO) {
        if (categorieClientDTO.getIdCategorieClient() != null) {
            categorieClientService.updateCategorieClient(categorieClientDTO.getIdCategorieClient(), categorieClientDTO);
        } else {
            categorieClientService.createCategorieClient(categorieClientDTO);
        }
        return "redirect:/categorie-clients";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategorieClient(@PathVariable Integer id) {
        categorieClientService.deleteCategorieClient(id);
        return "redirect:/categorie-clients";
    }
}