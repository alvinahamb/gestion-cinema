package com.example.demo.controller;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.dto.ReservationPlaceDTO;
import com.example.demo.service.ReservationService;
import com.example.demo.service.ReservationPlaceService;
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

    @Autowired
    private ReservationPlaceService reservationPlaceService;

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

    @GetMapping("/details/{id}")
    public String detailsReservation(@PathVariable Integer id, Model model) {
        Optional<ReservationDTO> reservation = reservationService.getReservationById(id);
        if (reservation.isPresent()) {
            // Récupérer les places de la réservation
            List<ReservationPlaceDTO> reservationPlaces = reservationPlaceService.getByReservationId(id);

            model.addAttribute("reservation", reservation.get());
            model.addAttribute("reservationPlaces", reservationPlaces);
            return "reservation/details";
        }
        return "redirect:/reservations";
    }

    @PostMapping("/delete/{id}")
    public String deleteReservation(@PathVariable Integer id) {
        reservationService.deleteReservation(id);
        return "redirect:/reservations";
    }
}
