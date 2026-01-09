package com.example.demo.controller;

import com.example.demo.dto.MethodePaiementDTO;
import com.example.demo.service.MethodePaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/methodes-paiement")
public class MethodePaiementMVCController {
    @Autowired
    private MethodePaiementService methodePaiementService;

    @GetMapping
    public String listMethodesPaiement(Model model) {
        List<MethodePaiementDTO> methodesPaiement = methodePaiementService.getAllMethodesPaiement();
        model.addAttribute("methodesPaiement", methodesPaiement);
        return "methode-paiement/list";
    }

    @GetMapping("/nouveau")
    public String newMethodePaiement(Model model) {
        model.addAttribute("methodePaiement", null);
        return "methode-paiement/form";
    }

    @GetMapping("/edit/{id}")
    public String editMethodePaiement(@PathVariable Integer id, Model model) {
        Optional<MethodePaiementDTO> methodePaiement = methodePaiementService.getMethodePaiementById(id);
        if (methodePaiement.isPresent()) {
            model.addAttribute("methodePaiement", methodePaiement.get());
            return "methode-paiement/form";
        }
        return "redirect:/methodes-paiement";
    }

    @PostMapping("/save")
    public String saveMethodePaiement(@ModelAttribute MethodePaiementDTO methodePaiementDTO) {
        methodePaiementService.createMethodePaiement(methodePaiementDTO);
        return "redirect:/methodes-paiement";
    }

    @PostMapping("/update")
    public String updateMethodePaiement(@RequestParam Integer id, @ModelAttribute MethodePaiementDTO methodePaiementDTO) {
        methodePaiementService.updateMethodePaiement(id, methodePaiementDTO);
        return "redirect:/methodes-paiement";
    }

    @PostMapping("/delete/{id}")
    public String deleteMethodePaiement(@PathVariable Integer id) {
        methodePaiementService.deleteMethodePaiement(id);
        return "redirect:/methodes-paiement";
    }
}
