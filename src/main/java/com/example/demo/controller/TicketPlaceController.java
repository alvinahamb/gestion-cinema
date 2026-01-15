package com.example.demo.controller;

import com.example.demo.dto.TicketPlaceDTO;
import com.example.demo.service.TicketPlaceService;
import com.example.demo.service.TicketService;
import com.example.demo.service.PlaceService;
import com.example.demo.service.CategorieClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ticket-places")
public class TicketPlaceController {
    @Autowired
    private TicketPlaceService ticketPlaceService;
    
    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private PlaceService placeService;
    
    @Autowired
    private CategorieClientService categorieClientService;

    @GetMapping
    public String listTicketPlaces(Model model) {
        List<TicketPlaceDTO> ticketPlaces = ticketPlaceService.getAllTicketPlaces();
        model.addAttribute("ticketPlaces", ticketPlaces);
        return "ticket-place/list";
    }

    @GetMapping("/nouveau")
    public String newTicketPlace(Model model) {
        model.addAttribute("ticketPlace", null);
        model.addAttribute("tickets", ticketService.getAllTickets());
        model.addAttribute("places", placeService.getAllPlaces());
        model.addAttribute("categoriesClient", categorieClientService.getAllCategorieClients());
        return "ticket-place/form";
    }

    @GetMapping("/edit/{id}")
    public String editTicketPlace(@PathVariable Integer id, Model model) {
        Optional<TicketPlaceDTO> ticketPlace = ticketPlaceService.getTicketPlaceById(id);
        if (ticketPlace.isPresent()) {
            model.addAttribute("ticketPlace", ticketPlace.get());
            model.addAttribute("tickets", ticketService.getAllTickets());
            model.addAttribute("places", placeService.getAllPlaces());
            model.addAttribute("categoriesClient", categorieClientService.getAllCategorieClients());
            return "ticket-place/form";
        }
        return "redirect:/ticket-places";
    }

    @PostMapping("/save")
    public String saveTicketPlace(@ModelAttribute TicketPlaceDTO dto) {
        ticketPlaceService.createTicketPlace(dto);
        return "redirect:/ticket-places";
    }

    @PostMapping("/update")
    public String updateTicketPlace(@RequestParam Integer id, @ModelAttribute TicketPlaceDTO dto) {
        ticketPlaceService.updateTicketPlace(id, dto);
        return "redirect:/ticket-places";
    }

    @PostMapping("/delete/{id}")
    public String deleteTicketPlace(@PathVariable Integer id) {
        ticketPlaceService.deleteTicketPlace(id);
        return "redirect:/ticket-places";
    }
}
