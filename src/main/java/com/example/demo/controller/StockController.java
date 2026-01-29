package com.example.demo.controller;

import com.example.demo.dto.MouvementStockDTO;
import com.example.demo.dto.ProduitDTO;
import com.example.demo.dto.StockDTO;
import com.example.demo.service.ProduitService;
import com.example.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/stocks")
public class StockController {
    @Autowired
    private StockService stockService;
    
    @Autowired
    private ProduitService produitService;

    @GetMapping
    public String listStocks(Model model) {
        List<StockDTO> stocks = stockService.getAllStocks();
        List<MouvementStockDTO> mouvements = stockService.getAllMouvements();
        model.addAttribute("stocks", stocks);
        model.addAttribute("mouvements", mouvements);
        return "stock/list";
    }

    @GetMapping("/mouvements/{produitId}")
    public String mouvementsByProduit(@PathVariable Integer produitId, Model model) {
        List<MouvementStockDTO> mouvements = stockService.getMouvementsByProduitId(produitId);
        ProduitDTO produit = produitService.getProduitById(produitId).orElse(null);
        StockDTO stock = stockService.getStockByProduitId(produitId).orElse(null);
        model.addAttribute("mouvements", mouvements);
        model.addAttribute("produit", produit);
        model.addAttribute("stock", stock);
        return "stock/mouvements";
    }
    
    @GetMapping("/entree")
    public String formEntree(Model model) {
        List<ProduitDTO> produits = produitService.getAllProduits();
        model.addAttribute("produits", produits);
        return "stock/entree";
    }
    
    @PostMapping("/entree")
    public String ajouterEntree(@RequestParam Integer produitId,
                                @RequestParam Integer quantite,
                                RedirectAttributes redirectAttributes) {
        try {
            stockService.ajouterEntree(produitId, quantite);
            redirectAttributes.addFlashAttribute("success", "Entrée de stock enregistrée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur: " + e.getMessage());
        }
        return "redirect:/stocks";
    }
}
