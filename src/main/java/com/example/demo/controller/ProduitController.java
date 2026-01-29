package com.example.demo.controller;

import com.example.demo.dto.ProduitDTO;
import com.example.demo.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/produits")
public class ProduitController {
    @Autowired
    private ProduitService produitService;

    @GetMapping
    public String listProduits(Model model) {
        List<ProduitDTO> produits = produitService.getAllProduits();
        model.addAttribute("produits", produits);
        return "produit/list";
    }

    @GetMapping("/nouveau")
    public String newProduit(Model model) {
        model.addAttribute("produit", null);
        return "produit/form";
    }

    @GetMapping("/edit/{id}")
    public String editProduit(@PathVariable Integer id, Model model) {
        Optional<ProduitDTO> produit = produitService.getProduitById(id);
        if (produit.isPresent()) {
            model.addAttribute("produit", produit.get());
            return "produit/form";
        }
        return "redirect:/produits";
    }

    @PostMapping("/save")
    public String saveProduit(@RequestParam(required = false) Integer id,
                              @RequestParam String nom,
                              @RequestParam BigDecimal prix,
                              RedirectAttributes redirectAttributes) {
        try {
            ProduitDTO produitDTO = new ProduitDTO();
            produitDTO.setNom(nom);
            produitDTO.setPrix(prix);
            
            if (id != null && id > 0) {
                produitService.updateProduit(id, produitDTO);
                redirectAttributes.addFlashAttribute("success", "Produit modifié avec succès");
            } else {
                produitService.createProduit(produitDTO);
                redirectAttributes.addFlashAttribute("success", "Produit créé avec succès");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur: " + e.getMessage());
        }
        return "redirect:/produits";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduit(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            if (produitService.deleteProduit(id)) {
                redirectAttributes.addFlashAttribute("success", "Produit supprimé avec succès");
            } else {
                redirectAttributes.addFlashAttribute("error", "Produit non trouvé");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression: " + e.getMessage());
        }
        return "redirect:/produits";
    }
}
