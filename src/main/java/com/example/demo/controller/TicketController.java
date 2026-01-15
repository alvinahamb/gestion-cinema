package com.example.demo.controller;

import com.example.demo.dto.TicketDTO;
import com.example.demo.dto.TicketPlaceDTO;
import com.example.demo.service.TicketService;
import com.example.demo.service.TicketPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketPlaceService ticketPlaceService;

    @GetMapping
    public String listTickets(Model model) {
        List<TicketDTO> tickets = ticketService.getAllTickets();
        
        // Calculer le montant restant pour chaque ticket
        for (TicketDTO ticket : tickets) {
            BigDecimal montantRestant = ticketService.getMontantRestantAPayer(ticket.getIdTicket());
            ticket.setMontantRestant(montantRestant);
        }
        
        model.addAttribute("tickets", tickets);
        return "ticket/list";
    }

    @GetMapping("/form")
    public String showForm(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            Optional<TicketDTO> ticket = ticketService.getTicketById(id);
            ticket.ifPresent(t -> model.addAttribute("ticket", t));
        } else {
            model.addAttribute("ticket", new TicketDTO());
        }
        return "ticket/form";
    }

    @PostMapping("/save")
    public String saveTicket(@ModelAttribute TicketDTO ticketDTO) {
        ticketService.createTicket(ticketDTO);
        return "redirect:/ticket";
    }

    @PostMapping("/update/{id}")
    public String updateTicket(@PathVariable Integer id, @ModelAttribute TicketDTO ticketDTO) {
        ticketService.updateTicket(id, ticketDTO);
        return "redirect:/ticket";
    }

    @GetMapping("/details/{id}")
    public String detailsTicket(@PathVariable Integer id, Model model) {
        Optional<TicketDTO> ticket = ticketService.getTicketById(id);
        if (ticket.isPresent()) {
            // Récupérer les places du ticket
            List<TicketPlaceDTO> ticketPlaces = ticketPlaceService.getByTicketId(id);

            model.addAttribute("ticket", ticket.get());
            model.addAttribute("ticketPlaces", ticketPlaces);
            return "ticket/details";
        }
        return "redirect:/ticket";
    }

    @GetMapping("/delete/{id}")
    public String deleteTicket(@PathVariable Integer id) {
        ticketService.deleteTicket(id);
        return "redirect:/ticket";
    }
}
