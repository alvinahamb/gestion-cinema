package com.example.demo.controller;

import com.example.demo.dto.SalleDTO;
import com.example.demo.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/salles")
public class SalleController {
    @Autowired
    private SalleService salleService;

    @GetMapping
    public String listSalles(Model model) {
        List<SalleDTO> salles = salleService.getAllSalles();
        model.addAttribute("salles", salles);
        return "salle/list";
    }

    @GetMapping("/nouveau")
    public String newSalle(Model model) {
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
    public String saveSalle(@ModelAttribute SalleDTO salleDTO) {
        salleService.createSalle(salleDTO);
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
