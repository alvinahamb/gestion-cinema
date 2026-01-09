package com.example.demo.controller;

import com.example.demo.dto.SeanceDTO;
import com.example.demo.service.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/seances")
public class SeanceController {
    @Autowired
    private SeanceService seanceService;

    @GetMapping
    public String listSeances(Model model) {
        List<SeanceDTO> seances = seanceService.getAllSeances();
        model.addAttribute("seances", seances);
        return "seance/list";
    }

    @GetMapping("/nouveau")
    public String newSeance(Model model) {
        model.addAttribute("seance", null);
        return "seance/form";
    }

    @GetMapping("/edit/{id}")
    public String editSeance(@PathVariable Integer id, Model model) {
        Optional<SeanceDTO> seance = seanceService.getSeanceById(id);
        if (seance.isPresent()) {
            model.addAttribute("seance", seance.get());
            return "seance/form";
        }
        return "redirect:/seances";
    }

    @PostMapping("/save")
    public String saveSeance(@ModelAttribute SeanceDTO seanceDTO) {
        seanceService.createSeance(seanceDTO);
        return "redirect:/seances";
    }

    @PostMapping("/update")
    public String updateSeance(@RequestParam Integer id, @ModelAttribute SeanceDTO seanceDTO) {
        seanceService.updateSeance(id, seanceDTO);
        return "redirect:/seances";
    }

    @PostMapping("/delete/{id}")
    public String deleteSeance(@PathVariable Integer id) {
        seanceService.deleteSeance(id);
        return "redirect:/seances";
    }
}
