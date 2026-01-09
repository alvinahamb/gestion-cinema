package com.example.demo.controller;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public String listReservations(Model model) {
        List<ReservationDTO> reservations = reservationService.getAllReservations();
        model.addAttribute("reservations", reservations);
        return "reservation/list";
    }

    @GetMapping("/nouveau")
    public String newReservation(Model model) {
        model.addAttribute("reservation", null);
        return "reservation/form";
    }

    @GetMapping("/edit/{id}")
    public String editReservation(@PathVariable Integer id, Model model) {
        Optional<ReservationDTO> reservation = reservationService.getReservationById(id);
        if (reservation.isPresent()) {
            model.addAttribute("reservation", reservation.get());
            return "reservation/form";
        }
        return "redirect:/reservations";
    }

    @PostMapping("/save")
    public String saveReservation(@ModelAttribute ReservationDTO reservationDTO) {
        reservationService.createReservation(reservationDTO);
        return "redirect:/reservations";
    }

    @PostMapping("/update")
    public String updateReservation(@RequestParam Integer id, @ModelAttribute ReservationDTO reservationDTO) {
        reservationService.updateReservation(id, reservationDTO);
        return "redirect:/reservations";
    }

    @PostMapping("/delete/{id}")
    public String deleteReservation(@PathVariable Integer id) {
        reservationService.deleteReservation(id);
        return "redirect:/reservations";
    }
}
