package com.example.demo.controller;

import com.example.demo.dto.LangueDTO;
import com.example.demo.service.LangueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/langues")
public class LangueController {
    @Autowired
    private LangueService languageService;

    @GetMapping
    public String listLangues(Model model) {
        List<LangueDTO> langues = languageService.getAllLangues();
        model.addAttribute("langues", langues);
        return "langue/list";
    }

    @GetMapping("/nouveau")
    public String newLangue(Model model) {
        model.addAttribute("langue", null);
        return "langue/form";
    }

    @GetMapping("/edit/{id}")
    public String editLangue(@PathVariable Integer id, Model model) {
        Optional<LangueDTO> langue = languageService.getLangueById(id);
        if (langue.isPresent()) {
            model.addAttribute("langue", langue.get());
            return "langue/form";
        }
        return "redirect:/langues";
    }

    @PostMapping("/save")
    public String saveLangue(@ModelAttribute LangueDTO languageDTO) {
        languageService.createLangue(languageDTO);
        return "redirect:/langues";
    }

    @PostMapping("/update")
    public String updateLangue(@RequestParam Integer id, @ModelAttribute LangueDTO languageDTO) {
        languageService.updateLangue(id, languageDTO);
        return "redirect:/langues";
    }

    @PostMapping("/delete/{id}")
    public String deleteLangue(@PathVariable Integer id) {
        languageService.deleteLangue(id);
        return "redirect:/langues";
    }
}
