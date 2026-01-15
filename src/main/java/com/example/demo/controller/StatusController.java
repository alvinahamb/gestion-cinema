package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.TypeStatutDTO;
import com.example.demo.service.StatusService;
import com.example.demo.service.TypeStatutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/status")
public class StatusController {
    @Autowired
    private StatusService statusService;
    @Autowired
    private TypeStatutService typeStatutService;

    @GetMapping
    public String listStatus(Model model) {
        List<StatusDTO> statusList = statusService.getAllStatus();
        model.addAttribute("statusList", statusList);
        return "status/list";
    }

    @GetMapping("/nouveau")
    public String newStatus(Model model) {
        List<TypeStatutDTO> typeStatuts = typeStatutService.getAllTypeStatuts();
        model.addAttribute("typeStatuts", typeStatuts);
        model.addAttribute("status", null);
        return "status/form";
    }

    @GetMapping("/edit/{id}")
    public String editStatus(@PathVariable Integer id, Model model) {
        Optional<StatusDTO> status = statusService.getStatusById(id);
        if (status.isPresent()) {
            List<TypeStatutDTO> typeStatuts = typeStatutService.getAllTypeStatuts();
            model.addAttribute("typeStatuts", typeStatuts);
            model.addAttribute("status", status.get());
            return "status/form";
        }
        return "redirect:/status";
    }

    @PostMapping("/save")
    public String saveStatus(@ModelAttribute StatusDTO statusDTO) {
        if (statusDTO.getIdStatus() != null) {
            statusService.updateStatus(statusDTO.getIdStatus(), statusDTO);
        } else {
            statusService.createStatus(statusDTO);
        }
        return "redirect:/status";
    }

    @PostMapping("/delete/{id}")
    public String deleteStatus(@PathVariable Integer id) {
        statusService.deleteStatus(id);
        return "redirect:/statuses";
    }
}
