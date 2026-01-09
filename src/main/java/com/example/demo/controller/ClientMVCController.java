package com.example.demo.controller;

import com.example.demo.dto.ClientDTO;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clients")
public class ClientMVCController {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public String listClients(Model model) {
        List<ClientDTO> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "client/list";
    }

    @GetMapping("/nouveau")
    public String newClient(Model model) {
        model.addAttribute("client", null);
        return "client/form";
    }

    @GetMapping("/edit/{id}")
    public String editClient(@PathVariable Integer id, Model model) {
        Optional<ClientDTO> client = clientService.getClientById(id);
        if (client.isPresent()) {
            model.addAttribute("client", client.get());
            return "client/form";
        }
        return "redirect:/clients";
    }

    @PostMapping("/save")
    public String saveClient(@ModelAttribute ClientDTO clientDTO) {
        clientService.createClient(clientDTO);
        return "redirect:/clients";
    }

    @PostMapping("/update")
    public String updateClient(@RequestParam Integer id, @ModelAttribute ClientDTO clientDTO) {
        clientService.updateClient(id, clientDTO);
        return "redirect:/clients";
    }

    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable Integer id) {
        clientService.deleteClient(id);
        return "redirect:/clients";
    }
}
