package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/ventes-produits")
public class VenteProduitController {
    @Autowired
    private VenteProduitService venteProduitService;
    
    @Autowired
    private ClientService clientService;
    
    @Autowired
    private SeanceService seanceService;
    
    @Autowired
    private ProduitService produitService;
    
    @Autowired
    private StockService stockService;

    @GetMapping
    public String listVentes(Model model) {
        List<VenteProduitDTO> ventes = venteProduitService.getAllVentes();
        model.addAttribute("ventes", ventes);
        return "vente-produit/list";
    }

    @GetMapping("/nouveau")
    public String newVente(Model model) {
        List<ClientDTO> clients = clientService.getAllClients();
        List<SeanceDTO> seances = seanceService.getAllSeances();
        List<ProduitDTO> produits = produitService.getAllProduits();
        List<StockDTO> stocks = stockService.getAllStocks();
        
        model.addAttribute("clients", clients);
        model.addAttribute("seances", seances);
        model.addAttribute("produits", produits);
        model.addAttribute("stocks", stocks);
        return "vente-produit/form";
    }

    @GetMapping("/details/{id}")
    public String detailsVente(@PathVariable Integer id, Model model) {
        Optional<VenteProduitDTO> vente = venteProduitService.getVenteById(id);
        if (vente.isPresent()) {
            model.addAttribute("vente", vente.get());
            return "vente-produit/details";
        }
        return "redirect:/ventes-produits";
    }

    @PostMapping("/save")
    public String saveVente(@RequestParam Integer clientId,
                            @RequestParam(required = false) Integer seanceId,
                            @RequestParam Map<String, String> allParams,
                            RedirectAttributes redirectAttributes) {
        try {
            // Extraire les produits et quantités du formulaire
            List<VenteProduitDetailDTO> details = new ArrayList<>();
            
            for (String key : allParams.keySet()) {
                if (key.startsWith("produit_")) {
                    String index = key.substring(8); // Enlever "produit_"
                    String produitIdStr = allParams.get(key);
                    String quantiteStr = allParams.get("quantite_" + index);
                    
                    if (produitIdStr != null && !produitIdStr.isEmpty() && 
                        quantiteStr != null && !quantiteStr.isEmpty()) {
                        Integer produitId = Integer.parseInt(produitIdStr);
                        Integer quantite = Integer.parseInt(quantiteStr);
                        
                        if (quantite > 0) {
                            VenteProduitDetailDTO detail = new VenteProduitDetailDTO();
                            detail.setProduitId(produitId);
                            detail.setQuantite(quantite);
                            details.add(detail);
                        }
                    }
                }
            }
            
            if (details.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Veuillez sélectionner au moins un produit");
                return "redirect:/ventes-produits/nouveau";
            }
            
            venteProduitService.createVente(clientId, seanceId, details);
            redirectAttributes.addFlashAttribute("success", "Vente enregistrée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur: " + e.getMessage());
        }
        return "redirect:/ventes-produits";
    }

    @PostMapping("/delete/{id}")
    public String deleteVente(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            if (venteProduitService.deleteVente(id)) {
                redirectAttributes.addFlashAttribute("success", "Vente supprimée avec succès");
            } else {
                redirectAttributes.addFlashAttribute("error", "Vente non trouvée");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression: " + e.getMessage());
        }
        return "redirect:/ventes-produits";
    }
}
