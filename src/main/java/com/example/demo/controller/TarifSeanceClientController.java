package com.example.demo.controller;

import com.example.demo.dto.TarifSeanceClientDTO;
import com.example.demo.service.TarifSeanceClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tarif-seance-clients")
public class TarifSeanceClientController {
    @Autowired
    private TarifSeanceClientService tarifSeanceClientService;

    @GetMapping
    public String listTarifSeanceClients(Model model) {
        List<TarifSeanceClientDTO> tarifSeanceClients = tarifSeanceClientService.getAllTarifSeanceClients();
        model.addAttribute("tarifSeanceClients", tarifSeanceClients);
        return "tarif-seance-client/list";
    }

    @GetMapping("/nouveau")
    public String newTarifSeanceClient(Model model) {
        model.addAttribute("tarifSeanceClient", null);
        return "tarif-seance-client/form";
    }

    @GetMapping("/{id}/modifier")
    public String editTarifSeanceClient(@PathVariable Integer id, Model model) {
        Optional<TarifSeanceClientDTO> tarifSeanceClient = tarifSeanceClientService.getTarifSeanceClientById(id);
        if (tarifSeanceClient.isPresent()) {
            model.addAttribute("tarifSeanceClient", tarifSeanceClient.get());
            return "tarif-seance-client/form";
        }
        return "redirect:/tarif-seance-clients";
    }

    @PostMapping
    public String saveTarifSeanceClient(@ModelAttribute TarifSeanceClientDTO tarifSeanceClientDTO) {
        if (tarifSeanceClientDTO.getId() != null) {
            tarifSeanceClientService.updateTarifSeanceClient(tarifSeanceClientDTO.getId(), tarifSeanceClientDTO);
        } else {
            tarifSeanceClientService.createTarifSeanceClient(tarifSeanceClientDTO);
        }
        return "redirect:/tarif-seance-clients";
    }

    @GetMapping("/{id}/supprimer")
    public String deleteTarifSeanceClient(@PathVariable Integer id) {
        tarifSeanceClientService.deleteTarifSeanceClient(id);
        return "redirect:/tarif-seance-clients";
    }
}