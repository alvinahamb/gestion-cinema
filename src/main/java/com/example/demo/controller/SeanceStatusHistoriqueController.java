package com.example.demo.controller;

import com.example.demo.dto.SeanceStatusHistoriqueDTO;
import com.example.demo.service.SeanceStatusHistoriqueService;
import com.example.demo.service.SeanceService;
import com.example.demo.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/seance-status-historiques")
public class SeanceStatusHistoriqueController {
    @Autowired
    private SeanceStatusHistoriqueService seanceStatusHistoriqueService;
    
    @Autowired
    private SeanceService seanceService;
    
    @Autowired
    private StatusService statusService;

    @GetMapping
    public String listSeanceStatusHistoriques(Model model) {
        List<SeanceStatusHistoriqueDTO> historiques = seanceStatusHistoriqueService.getAllSeanceStatusHistoriques();
        model.addAttribute("historiques", historiques);
        return "seance-status-historique/list";
    }

    @GetMapping("/nouveau")
    public String newSeanceStatusHistorique(Model model) {
        model.addAttribute("historique", null);
        model.addAttribute("seances", seanceService.getAllSeances());
        model.addAttribute("statuses", statusService.getAllStatus());
        return "seance-status-historique/form";
    }

    @GetMapping("/edit/{id}")
    public String editSeanceStatusHistorique(@PathVariable Integer id, Model model) {
        Optional<SeanceStatusHistoriqueDTO> historique = seanceStatusHistoriqueService.getSeanceStatusHistoriqueById(id);
        if (historique.isPresent()) {
            model.addAttribute("historique", historique.get());
            model.addAttribute("seances", seanceService.getAllSeances());
            model.addAttribute("statuses", statusService.getAllStatus());
            return "seance-status-historique/form";
        }
        return "redirect:/seance-status-historiques";
    }

    @PostMapping("/save")
    public String saveSeanceStatusHistorique(@ModelAttribute SeanceStatusHistoriqueDTO dto) {
        seanceStatusHistoriqueService.createSeanceStatusHistorique(dto);
        return "redirect:/seance-status-historiques";
    }

    @PostMapping("/update")
    public String updateSeanceStatusHistorique(@RequestParam Integer id, @ModelAttribute SeanceStatusHistoriqueDTO dto) {
        seanceStatusHistoriqueService.updateSeanceStatusHistorique(id, dto);
        return "redirect:/seance-status-historiques";
    }

    @PostMapping("/delete/{id}")
    public String deleteSeanceStatusHistorique(@PathVariable Integer id) {
        seanceStatusHistoriqueService.deleteSeanceStatusHistorique(id);
        return "redirect:/seance-status-historiques";
    }
}
