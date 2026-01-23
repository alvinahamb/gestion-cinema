package com.example.demo.controller;

import com.example.demo.dto.SocieteDTO;
import com.example.demo.service.SocieteService;
import com.example.demo.service.FactureSocieteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/societes")
public class SocieteController {
    @Autowired
    private SocieteService societeService;
    
    @Autowired
    private FactureSocieteService factureSocieteService;

    @GetMapping
    public String listSocietes(Model model) {
        List<SocieteDTO> societes = societeService.getAllSocietesWithFactures();
        model.addAttribute("societes", societes);
        return "societe/list";
    }

    @GetMapping("/nouveau")
    public String newSociete(Model model) {
        model.addAttribute("societe", null);
        return "societe/form";
    }

    @GetMapping("/edit/{id}")
    public String editSociete(@PathVariable Integer id, Model model) {
        Optional<SocieteDTO> societe = societeService.getSocieteById(id);
        if (societe.isPresent()) {
            model.addAttribute("societe", societe.get());
            return "societe/form";
        }
        return "redirect:/societes";
    }

    @PostMapping("/save")
    public String saveSociete(@ModelAttribute SocieteDTO societeDTO) {
        if (societeDTO.getId() != null) {
            societeService.updateSociete(societeDTO.getId(), societeDTO);
        } else {
            societeService.createSociete(societeDTO);
        }
        return "redirect:/societes";
    }

    @PostMapping("/delete/{id}")
    public String deleteSociete(@PathVariable Integer id) {
        societeService.deleteSociete(id);
        return "redirect:/societes";
    }
    
    @PostMapping("/facture/{idFacture}/paiement")
    public String addPaiement(@PathVariable Integer idFacture, 
                              @RequestParam("montant") BigDecimal montant) {
        factureSocieteService.addPaiement(idFacture, montant);
        return "redirect:/societes";
    }
}
