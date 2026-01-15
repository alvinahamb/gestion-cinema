package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.Ticket;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/achat")
public class AchatController {

    @Autowired
    private AchatService achatService;

    @Autowired
    private SeanceService seanceService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CategorieClientService categorieClientService;

    @Autowired
    private TarifSeanceClientService tarifSeanceClientService;

    @Autowired
    private TarifSeancePlaceService tarifSeancePlaceService;

    /**
     * Page d'achat de ticket pour une séance
     */
    @GetMapping("/seance/{seanceId}")
    public String showAchatPage(@PathVariable Integer seanceId, Model model) {
        Optional<SeanceDTO> seanceOpt = seanceService.getSeanceById(seanceId);
        
        if (seanceOpt.isEmpty()) {
            return "redirect:/seances";
        }

        SeanceDTO seance = seanceOpt.get();
        
        // Récupérer la salle et les places
        Integer salleId = seance.getSalle() != null ? seance.getSalle().getIdSalle() : null;
        
        if (salleId == null) {
            return "redirect:/seances";
        }

        // Récupérer les places avec leur statut d'occupation
        List<PlaceDTO> places = achatService.getPlacesBySalleWithOccupation(salleId, seanceId);

        // Récupérer les clients
        List<ClientDTO> clients = clientService.getAllClients();

        // Récupérer les catégories de clients
        List<CategorieClientDTO> categoriesClient = categorieClientService.getAllCategorieClients();

        // Récupérer les tarifs
        List<TarifSeanceClientDTO> tarifsClient = tarifSeanceClientService.getBySeanceId(seanceId);
        List<TarifSeancePlaceDTO> tarifsPlace = tarifSeancePlaceService.getBySeanceId(seanceId);

        model.addAttribute("seance", seance);
        model.addAttribute("places", places);
        model.addAttribute("clients", clients);
        model.addAttribute("categoriesClient", categoriesClient);
        model.addAttribute("tarifsClient", tarifsClient);
        model.addAttribute("tarifsPlace", tarifsPlace);
        model.addAttribute("salle", seance.getSalle());

        return "achat/form";
    }

    /**
     * Confirmer l'achat
     */
    @PostMapping("/confirmer")
    public String confirmerAchat(
            @RequestParam Integer seanceId,
            @RequestParam Integer clientId,
            @RequestParam(required = false) String placeIds,
            @RequestParam(required = false) String categorieIds,
            RedirectAttributes redirectAttributes) {

        try {
            if (placeIds == null || placeIds.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Veuillez sélectionner au moins une place");
                return "redirect:/achat/seance/" + seanceId;
            }

            List<Integer> placeIdList = Arrays.stream(placeIds.split(","))
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            List<Integer> categorieIdList = categorieIds != null && !categorieIds.isEmpty()
                    ? Arrays.stream(categorieIds.split(","))
                            .filter(s -> !s.isEmpty())
                            .map(Integer::parseInt)
                            .collect(Collectors.toList())
                    : List.of();

            if (placeIdList.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Veuillez sélectionner au moins une place");
                return "redirect:/achat/seance/" + seanceId;
            }

            Ticket ticket = achatService.createAchat(clientId, seanceId, placeIdList, categorieIdList);
            redirectAttributes.addFlashAttribute("success", "Achat confirmé avec succès ! Ticket #" + ticket.getIdTicket());
            return "redirect:/ticket";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'achat: " + e.getMessage());
            return "redirect:/achat/seance/" + seanceId;
        }
    }

    /**
     * API pour récupérer le tarif d'une place
     */
    @GetMapping("/api/tarif")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTarif(
            @RequestParam Integer seanceId,
            @RequestParam Integer categorieClientId,
            @RequestParam(required = false) Integer typePlaceId) {

        BigDecimal tarif = achatService.getTarif(seanceId, categorieClientId, typePlaceId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("tarif", tarif);
        
        return ResponseEntity.ok(response);
    }

    /**
     * API pour récupérer tous les tarifs d'une séance
     */
    @GetMapping("/api/tarifs/{seanceId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllTarifs(@PathVariable Integer seanceId) {
        Map<String, BigDecimal> tarifsClient = achatService.getAllTarifsForSeance(seanceId);
        List<TarifSeancePlaceDTO> tarifsPlace = achatService.getTarifsPlaceForSeance(seanceId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("tarifsClient", tarifsClient);
        response.put("tarifsPlace", tarifsPlace);
        
        return ResponseEntity.ok(response);
    }
}
