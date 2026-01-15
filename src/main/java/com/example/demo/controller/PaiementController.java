package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/paiement")
public class PaiementController {
    @Autowired
    private PaiementService paiementService;

    @Autowired
    private PaiementStatusHistoriqueService paiementStatusHistoriqueService;

    @Autowired
    private MethodePaiementService methodePaiementService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private PaiementDetailService paiementDetailService;

    @GetMapping
    public String listPaiements(Model model) {
        List<PaiementDTO> paiements = paiementService.getAllPaiements();
        model.addAttribute("paiements", paiements);
        return "paiement/list";
    }

    @GetMapping("/form")
    public String showForm(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            Optional<PaiementDTO> paiement = paiementService.getPaiementById(id);
            paiement.ifPresent(p -> model.addAttribute("paiement", p));
        } else {
            model.addAttribute("paiement", new PaiementDTO());
        }
        return "paiement/form";
    }

    @PostMapping("/save")
    public String savePaiement(@ModelAttribute PaiementDTO paiementDTO) {
        paiementService.createPaiement(paiementDTO);
        return "redirect:/paiement";
    }

    @PostMapping("/update/{id}")
    public String updatePaiement(@PathVariable Integer id, @ModelAttribute PaiementDTO paiementDTO) {
        paiementService.updatePaiement(id, paiementDTO);
        return "redirect:/paiement";
    }

    @GetMapping("/details/{id}")
    public String detailsPaiement(@PathVariable Integer id, Model model) {
        Optional<PaiementDTO> paiement = paiementService.getPaiementById(id);
        if (paiement.isPresent()) {
            // Récupérer l'historique des statuts
            List<PaiementStatusHistoriqueDTO> statusHistorique = paiementStatusHistoriqueService.getByPaiementId(id);
            
            // Récupérer les détails de paiement
            List<PaiementDetailDTO> paiementDetails = paiementDetailService.getByPaiementId(id);

            model.addAttribute("paiement", paiement.get());
            model.addAttribute("statusHistorique", statusHistorique);
            model.addAttribute("paiementDetails", paiementDetails);
            return "paiement/details";
        }
        return "redirect:/paiement";
    }

    @GetMapping("/delete/{id}")
    public String deletePaiement(@PathVariable Integer id) {
        paiementService.deletePaiement(id);
        return "redirect:/paiement";
    }

    /**
     * Page de paiement pour un ticket spécifique
     */
    @GetMapping("/ticket/{ticketId}")
    public String showPaiementPage(@PathVariable Integer ticketId, Model model) {
        Optional<TicketDTO> ticketOpt = ticketService.getTicketById(ticketId);
        
        if (ticketOpt.isEmpty()) {
            return "redirect:/ticket";
        }

        TicketDTO ticket = ticketOpt.get();
        
        // Récupérer le paiement associé au ticket
        Optional<PaiementDTO> paiementOpt = paiementService.getPaiementByTicketId(ticketId);
        
        if (paiementOpt.isEmpty()) {
            return "redirect:/ticket";
        }

        PaiementDTO paiement = paiementOpt.get();
        
        // Récupérer les méthodes de paiement
        List<MethodePaiementDTO> methodesPaiement = methodePaiementService.getAllMethodesPaiement();

        model.addAttribute("ticket", ticket);
        model.addAttribute("paiement", paiement);
        model.addAttribute("methodesPaiement", methodesPaiement);
        
        return "paiement/payer";
    }

    /**
     * Traiter le paiement
     */
    @PostMapping("/process")
    public String processPaiement(
            @RequestParam Integer idPaiement,
            @RequestParam Integer idMethodePaiement,
            @RequestParam java.math.BigDecimal montant,
            @RequestParam String datePaiement) {
        
        try {
            paiementService.processPaiement(idPaiement, idMethodePaiement, montant, 
                                          java.time.LocalDate.parse(datePaiement));
            return "redirect:/ticket";
        } catch (Exception e) {
            // En cas d'erreur, rediriger vers la page de paiement avec un message d'erreur
            return "redirect:/paiement/ticket/" + idPaiement + "?error=" + e.getMessage();
        }
    }
}
