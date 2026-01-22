package com.example.demo.controller;

import com.example.demo.dto.DiffusionDTO;
import com.example.demo.dto.PubliciteDTO;
import com.example.demo.dto.SocieteDTO;
import com.example.demo.dto.SocieteResumeDTO;
import com.example.demo.dto.TarifDiffusionDTO;
import com.example.demo.service.DiffusionService;
import com.example.demo.service.PubliciteService;
import com.example.demo.service.SocieteService;
import com.example.demo.service.TarifDiffusionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/diffusions")
public class DiffusionController {
    @Autowired
    private DiffusionService diffusionService;
    
    @Autowired
    private SocieteService societeService;
    
    @Autowired
    private PubliciteService publiciteService;
    
    @Autowired
    private TarifDiffusionService tarifDiffusionService;

    @GetMapping
    public String listDiffusions(
            @RequestParam(required = false) Integer mois,
            @RequestParam(required = false) Integer annee,
            Model model) {
        List<DiffusionDTO> diffusions = diffusionService.getAllDiffusions();
        model.addAttribute("diffusions", diffusions);
        
        // Résumé par société
        List<SocieteResumeDTO> resumesSocietes = diffusionService.getResumeBySociete();
        model.addAttribute("resumesSocietes", resumesSocietes);
        
        // Calcul du total des tarifs si mois et année sont fournis
        if (mois != null && annee != null) {
            java.math.BigDecimal totalTarifs = diffusionService.getTotalTarifsByMoisAnnee(mois, annee);
            model.addAttribute("totalTarifs", totalTarifs);
            model.addAttribute("selectedMois", mois);
            model.addAttribute("selectedAnnee", annee);
        }
        
        return "diffusion/list";
    }

    @GetMapping("/nouveau")
    public String newDiffusion(Model model) {
        model.addAttribute("diffusion", null);
        loadFormData(model);
        return "diffusion/form";
    }

    @GetMapping("/edit/{id}")
    public String editDiffusion(@PathVariable Integer id, Model model) {
        Optional<DiffusionDTO> diffusion = diffusionService.getDiffusionById(id);
        if (diffusion.isPresent()) {
            model.addAttribute("diffusion", diffusion.get());
            loadFormData(model);
            return "diffusion/form";
        }
        return "redirect:/diffusions";
    }

    @PostMapping("/save")
    public String saveDiffusion(@ModelAttribute DiffusionDTO diffusionDTO) {
        if (diffusionDTO.getId() != null) {
            diffusionService.updateDiffusion(diffusionDTO.getId(), diffusionDTO);
        } else {
            diffusionService.createDiffusion(diffusionDTO);
        }
        return "redirect:/diffusions";
    }

    @PostMapping("/delete/{id}")
    public String deleteDiffusion(@PathVariable Integer id) {
        diffusionService.deleteDiffusion(id);
        return "redirect:/diffusions";
    }
    
    private void loadFormData(Model model) {
        List<SocieteDTO> societes = societeService.getAllSocietes();
        List<PubliciteDTO> publicites = publiciteService.getAllPublicites();
        List<TarifDiffusionDTO> tarifDiffusions = tarifDiffusionService.getAllTarifDiffusions();
        
        model.addAttribute("societes", societes);
        model.addAttribute("publicites", publicites);
        model.addAttribute("tarifDiffusions", tarifDiffusions);
    }
}
