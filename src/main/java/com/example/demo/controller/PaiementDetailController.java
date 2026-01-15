package com.example.demo.controller;

import com.example.demo.dto.PaiementDetailDTO;
import com.example.demo.service.PaiementDetailService;
import com.example.demo.service.PaiementService;
import com.example.demo.service.MethodePaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/paiement-details")
public class PaiementDetailController {
    @Autowired
    private PaiementDetailService paiementDetailService;
    
    @Autowired
    private PaiementService paiementService;
    
    @Autowired
    private MethodePaiementService methodePaiementService;

    @GetMapping
    public String listPaiementDetails(Model model) {
        List<PaiementDetailDTO> paiementDetails = paiementDetailService.getAllPaiementDetails();
        model.addAttribute("paiementDetails", paiementDetails);
        return "paiement-detail/list";
    }

    @GetMapping("/nouveau")
    public String newPaiementDetail(Model model) {
        model.addAttribute("paiementDetail", null);
        model.addAttribute("paiements", paiementService.getAllPaiements());
        model.addAttribute("methodesPaiement", methodePaiementService.getAllMethodesPaiement());
        return "paiement-detail/form";
    }

    @GetMapping("/edit/{id}")
    public String editPaiementDetail(@PathVariable Integer id, Model model) {
        Optional<PaiementDetailDTO> paiementDetail = paiementDetailService.getPaiementDetailById(id);
        if (paiementDetail.isPresent()) {
            model.addAttribute("paiementDetail", paiementDetail.get());
            model.addAttribute("paiements", paiementService.getAllPaiements());
            model.addAttribute("methodesPaiement", methodePaiementService.getAllMethodesPaiement());
            return "paiement-detail/form";
        }
        return "redirect:/paiement-details";
    }

    @PostMapping("/save")
    public String savePaiementDetail(@ModelAttribute PaiementDetailDTO dto) {
        paiementDetailService.createPaiementDetail(dto);
        return "redirect:/paiement-details";
    }

    @PostMapping("/update")
    public String updatePaiementDetail(@RequestParam Integer id, @ModelAttribute PaiementDetailDTO dto) {
        paiementDetailService.updatePaiementDetail(id, dto);
        return "redirect:/paiement-details";
    }

    @PostMapping("/delete/{id}")
    public String deletePaiementDetail(@PathVariable Integer id) {
        paiementDetailService.deletePaiementDetail(id);
        return "redirect:/paiement-details";
    }
}
