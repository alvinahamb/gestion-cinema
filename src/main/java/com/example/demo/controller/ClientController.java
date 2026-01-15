package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private CategorieClientService categorieClientService;

    @GetMapping
    public String listClients(Model model) {
        List<ClientDTO> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "client/list";
    }

    @GetMapping("/nouveau")
    public String newClient(Model model) {
        List<CategorieClientDTO> categoriesClient = categorieClientService.getAllCategorieClients();
        model.addAttribute("categoriesClient", categoriesClient);
        model.addAttribute("client", null);
        return "client/form";
    }

    @GetMapping("/edit/{id}")
    public String editClient(@PathVariable Integer id, Model model) {
        Optional<ClientDTO> client = clientService.getClientById(id);
        if (client.isPresent()) {
            List<CategorieClientDTO> categoriesClient = categorieClientService.getAllCategorieClients();
            model.addAttribute("categoriesClient", categoriesClient);
            model.addAttribute("client", client.get());
            return "client/form";
        }
        return "redirect:/clients";
    }

    @PostMapping("/save")
    public String saveClient(@ModelAttribute ClientDTO clientDTO) {
        if (clientDTO.getIdClient() != null && clientDTO.getIdClient() > 0) {
            // C'est une mise à jour
            clientService.updateClient(clientDTO.getIdClient(), clientDTO);
        } else {
            // C'est une création
            clientService.createClient(clientDTO);
        }
        return "redirect:/clients";
    }

    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable Integer id) {
        clientService.deleteClient(id);
        return "redirect:/clients";
    }
}
