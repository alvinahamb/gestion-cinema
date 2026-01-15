package com.example.demo.controller;

import com.example.demo.dto.ReservationPlaceDTO;
import com.example.demo.service.ReservationPlaceService;
import com.example.demo.service.ReservationService;
import com.example.demo.service.PlaceService;
import com.example.demo.service.CategorieClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reservation-places")
public class ReservationPlaceController {
    @Autowired
    private ReservationPlaceService reservationPlaceService;
    
    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private PlaceService placeService;
    
    @Autowired
    private CategorieClientService categorieClientService;

    @GetMapping
    public String listReservationPlaces(Model model) {
        List<ReservationPlaceDTO> reservationPlaces = reservationPlaceService.getAllReservationPlaces();
        model.addAttribute("reservationPlaces", reservationPlaces);
        return "reservation-place/list";
    }

    @GetMapping("/nouveau")
    public String newReservationPlace(Model model) {
        model.addAttribute("reservationPlace", null);
        model.addAttribute("reservations", reservationService.getAllReservations());
        model.addAttribute("places", placeService.getAllPlaces());
        model.addAttribute("categoriesClient", categorieClientService.getAllCategorieClients());
        return "reservation-place/form";
    }

    @GetMapping("/edit/{id}")
    public String editReservationPlace(@PathVariable Integer id, Model model) {
        Optional<ReservationPlaceDTO> reservationPlace = reservationPlaceService.getReservationPlaceById(id);
        if (reservationPlace.isPresent()) {
            model.addAttribute("reservationPlace", reservationPlace.get());
            model.addAttribute("reservations", reservationService.getAllReservations());
            model.addAttribute("places", placeService.getAllPlaces());
            model.addAttribute("categoriesClient", categorieClientService.getAllCategorieClients());
            return "reservation-place/form";
        }
        return "redirect:/reservation-places";
    }

    @PostMapping("/save")
    public String saveReservationPlace(@ModelAttribute ReservationPlaceDTO dto) {
        reservationPlaceService.createReservationPlace(dto);
        return "redirect:/reservation-places";
    }

    @PostMapping("/update")
    public String updateReservationPlace(@RequestParam Integer id, @ModelAttribute ReservationPlaceDTO dto) {
        reservationPlaceService.updateReservationPlace(id, dto);
        return "redirect:/reservation-places";
    }

    @PostMapping("/delete/{id}")
    public String deleteReservationPlace(@PathVariable Integer id) {
        reservationPlaceService.deleteReservationPlace(id);
        return "redirect:/reservation-places";
    }
}
