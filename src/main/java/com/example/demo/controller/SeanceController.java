package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalTime;
import java.math.BigDecimal;

@Controller
@RequestMapping("/seances")
public class SeanceController {
    @Autowired
    private SeanceService seanceService;

    @Autowired
    private FilmVersionService filmVersionService;

    @Autowired
    private SalleService salleService;

    @Autowired
    private SeanceStatusHistoriqueService seanceStatusHistoriqueService;

    @Autowired
    private TarifSeanceClientService tarifSeanceClientService;

    @Autowired
    private TarifSeancePlaceService tarifSeanceClientPlaceService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private TypeStatutService typeStatutService;

    @Autowired
    private CategorieClientService categorieClientService;

    @Autowired
    private TypePlaceService typePlaceService;

    @GetMapping
    public String listSeances(Model model) {
        List<SeanceDTO> seances = seanceService.getAllSeances();
        model.addAttribute("seances", seances);
        return "seance/list";
    }

    @GetMapping("/search")
    public String searchSeances(@RequestParam(required = false) String filmTitre,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateDebut,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime heureDebut,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFin,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime heureFin,
                               Model model) {
        List<SeanceDTO> seances = seanceService.searchSeances(filmTitre, dateDebut, heureDebut, dateFin, heureFin);

        model.addAttribute("seances", seances);
        model.addAttribute("searchTerm", filmTitre);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("heureDebut", heureDebut);
        model.addAttribute("dateFin", dateFin);
        model.addAttribute("heureFin", heureFin);
        return "seance/result";
    }

    @GetMapping("/nouveau")
    public String newSeance(Model model) {
        // Pour la création, on ne peut pas filtrer par heure/date car elles ne sont pas encore saisies
        // On passe toutes les salles, la validation se fera côté client avec JavaScript
        List<FilmVersionDTO> filmVersions = filmVersionService.getAllFilmVersions();
        List<SalleDTO> salles = salleService.getAllSalles();
        List<CategorieClientDTO> categoriesClients = categorieClientService.getAllCategorieClients();
        List<TypePlaceDTO> typesPlaces = typePlaceService.getAllTypePlaces();

        model.addAttribute("seance", null);
        model.addAttribute("filmVersions", filmVersions);
        model.addAttribute("salles", salles);
        model.addAttribute("categoriesClients", categoriesClients);
        model.addAttribute("typesPlaces", typesPlaces);
        return "seance/form";
    }

    @GetMapping("/edit/{id}")
    public String editSeance(@PathVariable Integer id, Model model) {
        Optional<SeanceDTO> seance = seanceService.getSeanceById(id);
        if (seance.isPresent()) {
            // Récupérer toutes les données nécessaires pour les selects
            List<FilmVersionDTO> filmVersions = filmVersionService.getAllFilmVersions();
            List<SalleDTO> salles = salleService.getAllSalles();
            List<StatusDTO> statuses = statusService.getAllStatus();
            List<CategorieClientDTO> categoriesClients = categorieClientService.getAllCategorieClients();
            List<TypePlaceDTO> typesPlaces = typePlaceService.getAllTypePlaces();

            model.addAttribute("seance", seance.get());
            model.addAttribute("filmVersions", filmVersions);
            model.addAttribute("salles", salles);
            model.addAttribute("statusList", statuses);
            model.addAttribute("categoriesClients", categoriesClients);
            model.addAttribute("typesPlaces", typesPlaces);
            return "seance/form";
        }
        return "redirect:/seances";
    }

    @PostMapping("/save")
    public String saveSeance(@RequestParam(required = false) Integer idSeance,
                            @RequestParam Integer filmVersionId,
                            @RequestParam Integer[] salleIds,
                            @RequestParam String dateSeance,
                            @RequestParam String heureDebut,
                            @RequestParam BigDecimal tarif,
                            @RequestParam(required = false) Boolean avecSousTitre,
                            @RequestParam Map<String, String> allParams,
                            RedirectAttributes redirectAttributes) {

        try {
            // Si idSeance existe, on met à jour au lieu de créer
            if (idSeance != null && idSeance > 0) {
                SeanceDTO seanceDTO = new SeanceDTO();
                seanceDTO.setIdSeance(idSeance);
                seanceDTO.setDateSeance(LocalDate.parse(dateSeance));
                seanceDTO.setHeureDebut(LocalTime.parse(heureDebut));
                seanceDTO.setTarif(tarif);
                seanceDTO.setAvecSousTitre(avecSousTitre != null ? avecSousTitre : false);
                
                // Ajouter les IDs pour filmVersion et salle
                FilmVersionDTO filmVersion = new FilmVersionDTO();
                filmVersion.setId(filmVersionId);
                seanceDTO.setFilmVersion(filmVersion);
                
                SalleDTO salle = new SalleDTO();
                salle.setIdSalle(salleIds[0]); // Prendre la première salle
                seanceDTO.setSalle(salle);
                
                seanceService.updateSeanceWithDetails(idSeance, seanceDTO);
                
                // Mettre à jour les associations (tarifs, etc.)
                updateSeanceAssociations(idSeance, allParams, tarif);
                
                redirectAttributes.addFlashAttribute("success", "Séance modifiée avec succès");
                return "redirect:/seances";
            }
            
            // Récupérer la version du film
            FilmVersionDTO filmVersion = filmVersionService.getFilmVersionById(filmVersionId).orElse(null);
            if (filmVersion == null) {
                redirectAttributes.addFlashAttribute("error", "Version du film introuvable");
                return "redirect:/seances";
            }

            // Convertir la date et l'heure
            LocalDate date = LocalDate.parse(dateSeance);
            LocalTime heure = LocalTime.parse(heureDebut);

            // Vérifier les conflits d'horaire pour chaque salle
            List<Integer> sallesDisponibles = new java.util.ArrayList<>();
            for (Integer salleId : salleIds) {
                if (isSalleDisponible(salleId, date, heure)) {
                    sallesDisponibles.add(salleId);
                }
            }

            if (sallesDisponibles.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Aucune salle n'est disponible à cette heure");
                return "redirect:/seances";
            }

            // Trouver le statut "Prévue" avec type_statut "Séance"
            StatusDTO statusPrevue = getStatusPrevue();

            // Créer une séance pour chaque salle disponible
            for (Integer salleId : sallesDisponibles) {
                SeanceDTO seanceDTO = new SeanceDTO();
                seanceDTO.setIdSeance(idSeance); // Pour modification si nécessaire
                seanceDTO.setFilmVersion(filmVersion);

                SalleDTO salle = salleService.getSalleById(salleId).orElse(null);
                seanceDTO.setSalle(salle);

                seanceDTO.setDateSeance(date);
                seanceDTO.setHeureDebut(heure);
                seanceDTO.setTarif(tarif);
                seanceDTO.setAvecSousTitre(avecSousTitre != null ? avecSousTitre : false);
                seanceDTO.setStatus(statusPrevue);

                // Créer la séance
                SeanceDTO createdSeance = seanceService.createSeance(seanceDTO);

                // Créer automatiquement l'historique et les tarifs associés
                createSeanceAssociations(createdSeance, allParams, tarif);
            }

            int seancesCrees = sallesDisponibles.size();
            redirectAttributes.addFlashAttribute("success",
                seancesCrees + " séance(s) créée(s) avec succès");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création des séances: " + e.getMessage());
        }

        return "redirect:/seances";
    }

    @PostMapping("/update")
    public String updateSeance(@RequestParam Integer id, @ModelAttribute SeanceDTO seanceDTO) {
        seanceService.updateSeance(id, seanceDTO);
        return "redirect:/seances";
    }

    @GetMapping("/tarifs/{id}")
    public String tarifsSeance(@PathVariable Integer id, Model model) {
        Optional<SeanceDTO> seance = seanceService.getSeanceById(id);
        if (seance.isPresent()) {
            // Récupérer les tarifs par catégorie client
            List<TarifSeanceClientDTO> tarifsClient = tarifSeanceClientService.getBySeanceId(id);
            
            // Récupérer les tarifs par catégorie client et type de place
            List<TarifSeancePlaceDTO> tarifsPlace = tarifSeanceClientPlaceService.getBySeanceId(id);
            
            model.addAttribute("seance", seance.get());
            model.addAttribute("tarifsClient", tarifsClient);
            model.addAttribute("tarifsPlace", tarifsPlace);
            return "seance/tarifs";
        }
        return "redirect:/seances";
    }

    @PostMapping("/delete/{id}")
    public String deleteSeance(@PathVariable Integer id) {
        seanceService.deleteSeance(id);
        return "redirect:/seances";
    }

    private boolean isSalleDisponible(Integer salleId, LocalDate date, LocalTime heure) {
        // Récupérer toutes les séances pour cette salle et cette date
        List<SeanceDTO> seances = seanceService.getAllSeances().stream()
                .filter(s -> s.getSalle() != null && s.getSalle().getIdSalle().equals(salleId))
                .filter(s -> s.getDateSeance().equals(date))
                .collect(java.util.stream.Collectors.toList());

        // Vérifier s'il y a un conflit d'horaire
        for (SeanceDTO seance : seances) {
            // Supposons une durée de 2 heures par défaut pour chaque séance
            LocalTime heureFin = seance.getHeureDebut().plusHours(2);
            if (heure.isBefore(heureFin) && heureFin.isAfter(heure)) {
                return false; // Conflit détecté
            }
        }

        return true; // Salle disponible
    }

    private StatusDTO getStatusPrevue() {
        // Trouver le statut "Prévue" avec type_statut "Séance"
        // Note: La base ne gère pas les accents, donc on recherche "Prevue" sans accent
        List<TypeStatutDTO> typeStatuts = typeStatutService.getAllTypeStatuts();
        TypeStatutDTO typeSeance = typeStatuts.stream()
                .filter(ts -> "Séance".equals(ts.getLibelle()))
                .findFirst().orElse(null);

        if (typeSeance != null) {
            List<StatusDTO> statuses = statusService.getAllStatus();
            return statuses.stream()
                    .filter(s -> "SEANCE_PREVUE".equals(s.getCode()) &&
                               s.getTypeStatut() != null &&
                               s.getTypeStatut().getIdTypeStatut().equals(typeSeance.getIdTypeStatut()))
                    .findFirst().orElse(null);
        }

        return null;
    }

    private void createSeanceAssociations(SeanceDTO seance, Map<String, String> customTarifs, BigDecimal defaultTarif) {
        // Créer l'historique de statut si le statut est défini
        if (seance.getStatus() != null) {
            SeanceStatusHistoriqueDTO historiqueDTO = new SeanceStatusHistoriqueDTO();
            historiqueDTO.setSeance(seance);
            historiqueDTO.setStatus(seance.getStatus());
            seanceStatusHistoriqueService.createSeanceStatusHistorique(historiqueDTO);
        }

        // Créer les tarifs pour toutes les catégories de clients
        List<CategorieClientDTO> categoriesClients = categorieClientService.getAllCategorieClients();
        for (CategorieClientDTO categorie : categoriesClients) {
            BigDecimal tarifClient = getCustomTarif(customTarifs, "tarifClient_" + categorie.getIdCategorieClient(), defaultTarif);
            // Ne créer que si le tarif diffère du tarif par défaut
            if (tarifClient.compareTo(defaultTarif) != 0) {
                TarifSeanceClientDTO tarifClientDTO = new TarifSeanceClientDTO();
                tarifClientDTO.setSeance(seance);
                tarifClientDTO.setCategorieClient(categorie);
                tarifClientDTO.setTarif(tarifClient);
                tarifSeanceClientService.createTarifSeanceClient(tarifClientDTO);
            }
        }

        // Créer les tarifs pour toutes les combinaisons catégorie client + type place
        List<TypePlaceDTO> typesPlaces = typePlaceService.getAllTypePlaces();
        for (CategorieClientDTO categorie : categoriesClients) {
            for (TypePlaceDTO typePlace : typesPlaces) {
                BigDecimal tarifPlace = getCustomTarif(customTarifs,
                    "tarifPlace_" + categorie.getIdCategorieClient() + "_" + typePlace.getIdTypePlace(), defaultTarif);
                // Ne créer que si le tarif diffère du tarif par défaut
                if (tarifPlace.compareTo(defaultTarif) != 0) {
                    TarifSeancePlaceDTO tarifPlaceDTO = new TarifSeancePlaceDTO();
                    tarifPlaceDTO.setSeance(seance);
                    tarifPlaceDTO.setCategorieClient(categorie);
                    tarifPlaceDTO.setTypePlace(typePlace);
                    tarifPlaceDTO.setTarif(tarifPlace);
                    tarifSeanceClientPlaceService.createTarifSeancePlace(tarifPlaceDTO);
                }
            }
        }
    }

    private void updateSeanceAssociations(Integer seanceId, Map<String, String> customTarifs, BigDecimal defaultTarif) {
        // Récupérer la séance mise à jour
        Optional<SeanceDTO> optSeance = seanceService.getSeanceById(seanceId);
        if (!optSeance.isPresent()) {
            return;
        }
        SeanceDTO seance = optSeance.get();
        
        // Supprimer les anciens tarifs personnalisés pour cette séance
        // Les méthodes de suppression doivent être créées dans les services si elles n'existent pas
        // Pour l'instant, on va juste recréer (en pratique, il faudrait supprimer d'abord)
        
        // Recréer les tarifs pour toutes les catégories de clients
        List<CategorieClientDTO> categoriesClients = categorieClientService.getAllCategorieClients();
        for (CategorieClientDTO categorie : categoriesClients) {
            BigDecimal tarifClient = getCustomTarif(customTarifs, "tarifClient_" + categorie.getIdCategorieClient(), defaultTarif);
            // Ne créer que si le tarif diffère du tarif par défaut
            if (tarifClient.compareTo(defaultTarif) != 0) {
                TarifSeanceClientDTO tarifClientDTO = new TarifSeanceClientDTO();
                tarifClientDTO.setSeance(seance);
                tarifClientDTO.setCategorieClient(categorie);
                tarifClientDTO.setTarif(tarifClient);
                tarifSeanceClientService.createTarifSeanceClient(tarifClientDTO);
            }
        }

        // Recréer les tarifs pour toutes les combinaisons catégorie client + type place
        List<TypePlaceDTO> typesPlaces = typePlaceService.getAllTypePlaces();
        for (CategorieClientDTO categorie : categoriesClients) {
            for (TypePlaceDTO typePlace : typesPlaces) {
                BigDecimal tarifPlace = getCustomTarif(customTarifs,
                    "tarifPlace_" + categorie.getIdCategorieClient() + "_" + typePlace.getIdTypePlace(), defaultTarif);
                // Ne créer que si le tarif diffère du tarif par défaut
                if (tarifPlace.compareTo(defaultTarif) != 0) {
                    TarifSeancePlaceDTO tarifPlaceDTO = new TarifSeancePlaceDTO();
                    tarifPlaceDTO.setSeance(seance);
                    tarifPlaceDTO.setCategorieClient(categorie);
                    tarifPlaceDTO.setTypePlace(typePlace);
                    tarifPlaceDTO.setTarif(tarifPlace);
                    tarifSeanceClientPlaceService.createTarifSeancePlace(tarifPlaceDTO);
                }
            }
        }
    }

    private BigDecimal getCustomTarif(Map<String, String> customTarifs, String key, BigDecimal defaultTarif) {
        String value = customTarifs.get(key);
        if (value != null && !value.trim().isEmpty()) {
            try {
                BigDecimal parsedValue = new BigDecimal(value.trim());
                // Si la valeur est 0 ou négative, utiliser le tarif par défaut
                if (parsedValue.compareTo(BigDecimal.ZERO) <= 0) {
                    return defaultTarif;
                }
                return parsedValue;
            } catch (NumberFormatException e) {
                // En cas d'erreur de format, utiliser le tarif par défaut
            }
        }
        return defaultTarif;
    }
}
