package com.example.demo.controller;

import com.example.demo.dto.DiffusionDTO;
import com.example.demo.dto.PaiementSocieteDTO;
import com.example.demo.service.DiffusionService;
import com.example.demo.service.PaiementSocieteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/paiement-societes")
public class PaiementSocieteController {
    @Autowired
    private PaiementSocieteService paiementSocieteService;
    
    @Autowired
    private DiffusionService diffusionService;

    @GetMapping
    public String listPaiementsSociete(Model model) {
        List<PaiementSocieteDTO> paiements = paiementSocieteService.getAllPaiementsSociete();
        model.addAttribute("paiements", paiements);
        return "paiement-societe/list";
    }

    @GetMapping("/nouveau")
    public String newPaiementSociete(@RequestParam(required = false) Integer diffusionId, Model model) {
        model.addAttribute("paiement", null);
        model.addAttribute("selectedDiffusionId", diffusionId);
        loadFormData(model);
        return "paiement-societe/form";
    }

    @GetMapping("/edit/{id}")
    public String editPaiementSociete(@PathVariable Integer id, Model model) {
        Optional<PaiementSocieteDTO> paiement = paiementSocieteService.getPaiementSocieteById(id);
        if (paiement.isPresent()) {
            model.addAttribute("paiement", paiement.get());
            loadFormData(model);
            return "paiement-societe/form";
        }
        return "redirect:/paiement-societes";
    }

    @PostMapping("/save")
    public String savePaiementSociete(@ModelAttribute PaiementSocieteDTO paiementDTO) {
        if (paiementDTO.getId() != null) {
            paiementSocieteService.updatePaiementSociete(paiementDTO.getId(), paiementDTO);
        } else {
            paiementSocieteService.createPaiementSociete(paiementDTO);
        }
        return "redirect:/paiement-societes";
    }

    @PostMapping("/delete/{id}")
    public String deletePaiementSociete(@PathVariable Integer id) {
        paiementSocieteService.deletePaiementSociete(id);
        return "redirect:/paiement-societes";
    }

    @GetMapping("/diffusion/{diffusionId}")
    public String listPaiementsByDiffusion(@PathVariable Integer diffusionId, Model model) {
        List<PaiementSocieteDTO> paiements = paiementSocieteService.getPaiementsByDiffusionId(diffusionId);
        Optional<DiffusionDTO> diffusion = diffusionService.getDiffusionById(diffusionId);
        
        model.addAttribute("paiements", paiements);
        model.addAttribute("diffusion", diffusion.orElse(null));
        model.addAttribute("montantRestant", paiementSocieteService.getMontantRestantByDiffusionId(diffusionId));
        model.addAttribute("sommePaiements", paiementSocieteService.getSommePaiementsByDiffusionId(diffusionId));
        
        return "paiement-societe/list-by-diffusion";
    }
    
    private void loadFormData(Model model) {
        List<DiffusionDTO> diffusions = diffusionService.getAllDiffusions();
        model.addAttribute("diffusions", diffusions);
    }
}
