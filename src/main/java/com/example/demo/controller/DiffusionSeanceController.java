package com.example.demo.controller;

import com.example.demo.dto.DiffusionDTO;
import com.example.demo.dto.DiffusionSeanceDTO;
import com.example.demo.dto.SeanceDTO;
import com.example.demo.service.DiffusionSeanceService;
import com.example.demo.service.DiffusionService;
import com.example.demo.service.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/diffusion-seances")
public class DiffusionSeanceController {

    @Autowired
    private DiffusionSeanceService diffusionSeanceService;

    @Autowired
    private DiffusionService diffusionService;

    @Autowired
    private SeanceService seanceService;

    @GetMapping("/attribuer/{idDiffusion}")
    public String showAttributionForm(@PathVariable Integer idDiffusion, Model model) {
        Optional<DiffusionDTO> diffusion = diffusionService.getDiffusionById(idDiffusion);
        if (diffusion.isEmpty()) {
            return "redirect:/diffusions";
        }
        
        // Récupérer toutes les séances disponibles
        List<SeanceDTO> seances = seanceService.getAllSeances();
        
        // Récupérer les attributions existantes pour cette diffusion
        List<DiffusionSeanceDTO> attributions = diffusionSeanceService.getByDiffusionId(idDiffusion);
        
        model.addAttribute("diffusion", diffusion.get());
        model.addAttribute("seances", seances);
        model.addAttribute("attributions", attributions);
        model.addAttribute("diffusionSeance", new DiffusionSeanceDTO());
        
        return "diffusion-seance/attribution";
    }

    @PostMapping("/save")
    public String saveAttribution(@RequestParam Integer idDiffusion, 
                                  @RequestParam Integer idSeance,
                                  @RequestParam(defaultValue = "1") Integer nombre) {
        DiffusionSeanceDTO dto = new DiffusionSeanceDTO();
        dto.setIdDiffusion(idDiffusion);
        dto.setIdSeance(idSeance);
        dto.setNombre(nombre);
        dto.setDateAjout(LocalDate.now());
        
        diffusionSeanceService.createDiffusionSeance(dto);
        
        return "redirect:/diffusion-seances/attribuer/" + idDiffusion;
    }

    @PostMapping("/delete/{id}")
    public String deleteAttribution(@PathVariable Integer id, @RequestParam Integer idDiffusion) {
        diffusionSeanceService.deleteDiffusionSeance(id);
        return "redirect:/diffusion-seances/attribuer/" + idDiffusion;
    }
}
