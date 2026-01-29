package com.example.demo.controller;

import com.example.demo.dto.PaiementProduitDTO;
import com.example.demo.service.PaiementProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/paiements-produits")
public class PaiementProduitController {
    @Autowired
    private PaiementProduitService paiementProduitService;

    @GetMapping
    public String listPaiements(Model model) {
        List<PaiementProduitDTO> paiements = paiementProduitService.getAllPaiements();
        model.addAttribute("paiements", paiements);
        return "paiement-produit/list";
    }

    @GetMapping("/details/{id}")
    public String detailsPaiement(@PathVariable Integer id, Model model) {
        Optional<PaiementProduitDTO> paiement = paiementProduitService.getPaiementById(id);
        if (paiement.isPresent()) {
            model.addAttribute("paiement", paiement.get());
            return "paiement-produit/details";
        }
        return "redirect:/paiements-produits";
    }
}
