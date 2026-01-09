package com.example.demo.controller;

import com.example.demo.dto.PaysDTO;
import com.example.demo.service.PaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pays")
public class PaysController {
    @Autowired
    private PaysService paysService;

    @GetMapping
    public String listPays(Model model) {
        List<PaysDTO> pays = paysService.getAllPays();
        model.addAttribute("pays", pays);
        return "pays/list";
    }

    @GetMapping("/nouveau")
    public String newPays(Model model) {
        model.addAttribute("pay", null);
        return "pays/form";
    }

    @GetMapping("/edit/{id}")
    public String editPays(@PathVariable Integer id, Model model) {
        Optional<PaysDTO> pays = paysService.getPaysById(id);
        if (pays.isPresent()) {
            model.addAttribute("pay", pays.get());
            return "pays/form";
        }
        return "redirect:/pays";
    }

    @PostMapping("/save")
    public String savePays(@ModelAttribute PaysDTO paysDTO) {
        paysService.createPays(paysDTO);
        return "redirect:/pays";
    }

    @PostMapping("/update")
    public String updatePays(@RequestParam Integer id, @ModelAttribute PaysDTO paysDTO) {
        paysService.updatePays(id, paysDTO);
        return "redirect:/pays";
    }

    @PostMapping("/delete/{id}")
    public String deletePays(@PathVariable Integer id) {
        paysService.deletePays(id);
        return "redirect:/pays";
    }
}
