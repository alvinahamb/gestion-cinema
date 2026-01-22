package com.example.demo.controller;

import com.example.demo.dto.TarifDiffusionDTO;
import com.example.demo.service.TarifDiffusionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tarif-diffusions")
public class TarifDiffusionController {
    @Autowired
    private TarifDiffusionService tarifDiffusionService;

    @GetMapping
    public String listTarifDiffusions(Model model) {
        List<TarifDiffusionDTO> tarifDiffusions = tarifDiffusionService.getAllTarifDiffusions();
        model.addAttribute("tarifDiffusions", tarifDiffusions);
        return "tarif-diffusion/list";
    }

    @GetMapping("/nouveau")
    public String newTarifDiffusion(Model model) {
        model.addAttribute("tarifDiffusion", null);
        return "tarif-diffusion/form";
    }

    @GetMapping("/edit/{id}")
    public String editTarifDiffusion(@PathVariable Integer id, Model model) {
        Optional<TarifDiffusionDTO> tarifDiffusion = tarifDiffusionService.getTarifDiffusionById(id);
        if (tarifDiffusion.isPresent()) {
            model.addAttribute("tarifDiffusion", tarifDiffusion.get());
            return "tarif-diffusion/form";
        }
        return "redirect:/tarif-diffusions";
    }

    @PostMapping("/save")
    public String saveTarifDiffusion(@ModelAttribute TarifDiffusionDTO tarifDiffusionDTO) {
        if (tarifDiffusionDTO.getId() != null) {
            tarifDiffusionService.updateTarifDiffusion(tarifDiffusionDTO.getId(), tarifDiffusionDTO);
        } else {
            tarifDiffusionService.createTarifDiffusion(tarifDiffusionDTO);
        }
        return "redirect:/tarif-diffusions";
    }

    @PostMapping("/delete/{id}")
    public String deleteTarifDiffusion(@PathVariable Integer id) {
        tarifDiffusionService.deleteTarifDiffusion(id);
        return "redirect:/tarif-diffusions";
    }
}
