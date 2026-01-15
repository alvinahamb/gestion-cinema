package com.example.demo.controller;

import com.example.demo.dto.PaiementStatusHistoriqueDTO;
import com.example.demo.service.PaiementStatusHistoriqueService;
import com.example.demo.service.PaiementService;
import com.example.demo.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/paiement-status-historiques")
public class PaiementStatusHistoriqueController {
    @Autowired
    private PaiementStatusHistoriqueService paiementStatusHistoriqueService;
    
    @Autowired
    private PaiementService paiementService;
    
    @Autowired
    private StatusService statusService;

    @GetMapping
    public String listPaiementStatusHistoriques(Model model) {
        List<PaiementStatusHistoriqueDTO> historiques = paiementStatusHistoriqueService.getAllPaiementStatusHistoriques();
        model.addAttribute("historiques", historiques);
        return "paiement-status-historique/list";
    }

    @GetMapping("/nouveau")
    public String newPaiementStatusHistorique(Model model) {
        model.addAttribute("historique", null);
        model.addAttribute("paiements", paiementService.getAllPaiements());
        model.addAttribute("statuses", statusService.getAllStatus());
        return "paiement-status-historique/form";
    }

    @GetMapping("/edit/{id}")
    public String editPaiementStatusHistorique(@PathVariable Integer id, Model model) {
        Optional<PaiementStatusHistoriqueDTO> historique = paiementStatusHistoriqueService.getPaiementStatusHistoriqueById(id);
        if (historique.isPresent()) {
            model.addAttribute("historique", historique.get());
            model.addAttribute("paiements", paiementService.getAllPaiements());
            model.addAttribute("statuses", statusService.getAllStatus());
            return "paiement-status-historique/form";
        }
        return "redirect:/paiement-status-historiques";
    }

    @PostMapping("/save")
    public String savePaiementStatusHistorique(@ModelAttribute PaiementStatusHistoriqueDTO dto) {
        paiementStatusHistoriqueService.createPaiementStatusHistorique(dto);
        return "redirect:/paiement-status-historiques";
    }

    @PostMapping("/update")
    public String updatePaiementStatusHistorique(@RequestParam Integer id, @ModelAttribute PaiementStatusHistoriqueDTO dto) {
        paiementStatusHistoriqueService.updatePaiementStatusHistorique(id, dto);
        return "redirect:/paiement-status-historiques";
    }

    @PostMapping("/delete/{id}")
    public String deletePaiementStatusHistorique(@PathVariable Integer id) {
        paiementStatusHistoriqueService.deletePaiementStatusHistorique(id);
        return "redirect:/paiement-status-historiques";
    }
}
