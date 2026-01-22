package com.example.demo.controller;

import com.example.demo.dto.PubliciteDTO;
import com.example.demo.service.PubliciteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/publicites")
public class PubliciteController {
    @Autowired
    private PubliciteService publiciteService;

    @GetMapping
    public String listPublicites(Model model) {
        List<PubliciteDTO> publicites = publiciteService.getAllPublicites();
        model.addAttribute("publicites", publicites);
        return "publicite/list";
    }

    @GetMapping("/nouveau")
    public String newPublicite(Model model) {
        model.addAttribute("publicite", null);
        return "publicite/form";
    }

    @GetMapping("/edit/{id}")
    public String editPublicite(@PathVariable Integer id, Model model) {
        Optional<PubliciteDTO> publicite = publiciteService.getPubliciteById(id);
        if (publicite.isPresent()) {
            model.addAttribute("publicite", publicite.get());
            return "publicite/form";
        }
        return "redirect:/publicites";
    }

    @PostMapping("/save")
    public String savePublicite(@ModelAttribute PubliciteDTO publiciteDTO) {
        if (publiciteDTO.getId() != null) {
            publiciteService.updatePublicite(publiciteDTO.getId(), publiciteDTO);
        } else {
            publiciteService.createPublicite(publiciteDTO);
        }
        return "redirect:/publicites";
    }

    @PostMapping("/delete/{id}")
    public String deletePublicite(@PathVariable Integer id) {
        publiciteService.deletePublicite(id);
        return "redirect:/publicites";
    }
}
