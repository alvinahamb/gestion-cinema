package com.example.demo.controller;

import com.example.demo.dto.TypeStatutDTO;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/type-statuts")
public class TypeStatutController {
    @Autowired
    private TypeStatutService typeStatutService;

    @GetMapping
    public String listTypeStatuts(Model model) {
        List<TypeStatutDTO> typeStatuts = typeStatutService.getAllTypeStatuts();
        model.addAttribute("typeStatuts", typeStatuts);
        return "type-statut/list";
    }

    @GetMapping("/nouveau")
    public String newTypeStatut(Model model) {
        model.addAttribute("typeStatut", null);
        return "type-statut/form";
    }

    @GetMapping("/edit/{id}")
    public String editTypeStatut(@PathVariable Integer id, Model model) {
        Optional<TypeStatutDTO> typeStatut = typeStatutService.getTypeStatutById(id);
        if (typeStatut.isPresent()) {
            model.addAttribute("typeStatut", typeStatut.get());
            return "type-statut/form";
        }
        return "redirect:/type-statuts";
    }

    @PostMapping("/save")
    public String saveTypeStatut(@ModelAttribute TypeStatutDTO typeStatutDTO) {
        if (typeStatutDTO.getIdTypeStatut() != null) {
            typeStatutService.updateTypeStatut(typeStatutDTO.getIdTypeStatut(), typeStatutDTO);
        } else {
            typeStatutService.createTypeStatut(typeStatutDTO);
        }
        return "redirect:/type-statuts";
    }

    @PostMapping("/delete/{id}")
    public String deleteTypeStatut(@PathVariable Integer id) {
        typeStatutService.deleteTypeStatut(id);
        return "redirect:/type-statuts";
    }
}