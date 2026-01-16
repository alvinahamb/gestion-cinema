package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AchatService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketPlaceRepository ticketPlaceRepository;

    @Autowired
    private ReservationPlaceRepository reservationPlaceRepository;

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private CategorieClientRepository categorieClientRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private TarifPlaceRepository tarifPlaceRepository;

    @Autowired
    private RemiseCategorieRepository remiseCategorieRepository;

    @Autowired
    private PaiementService paiementService;

    /**
     * Récupère les places de la salle avec leur statut d'occupation
     */
    public List<PlaceDTO> getPlacesBySalleWithOccupation(Integer salleId, Integer seanceId) {
        List<Place> places = placeRepository.findBySalleIdSalle(salleId);
        
        // Récupérer les places occupées par les tickets
        Set<Integer> occupiedPlaceIds = new HashSet<>(ticketPlaceRepository.findOccupiedPlaceIdsBySeanceId(seanceId));
        
        // Ajouter les places occupées par les réservations
        occupiedPlaceIds.addAll(reservationPlaceRepository.findOccupiedPlaceIdsBySeanceId(seanceId));

        return places.stream().map(place -> {
            PlaceDTO dto = new PlaceDTO();
            dto.setIdPlace(place.getIdPlace());
            dto.setNumeroLigne(place.getNumeroLigne());
            dto.setNumeroColonne(place.getNumeroColonne());
            dto.setOccupee(occupiedPlaceIds.contains(place.getIdPlace()));
            
            dto.setIdSalle(place.getSalle().getIdSalle());
            dto.setIdTypePlace(place.getTypePlace().getIdTypePlace());
            
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * Récupère le tarif en fonction du type de place et de la catégorie client
     * Règle de gestion:
     * 1. Chercher d'abord une remise spécifique (typePlaceId, categorieClientId)
     * 2. Sinon chercher une remise générale (id_type_place = NULL, categorieClientId)
     * 3. Si remise trouvée:
     *    - Si tarif de remise != NULL: utiliser ce tarif
     *    - Sinon si pourcentage != NULL: calculer tarif_place - (tarif_place * pourcentage / 100)
     * 4. Sinon: utiliser le tarif_place
     * 5. En dernier recours: utiliser le tarif de la séance
     */
    public BigDecimal getTarif(Integer seanceId, Integer categorieClientId, Integer typePlaceId) {
        // Récupérer le tarif de base depuis tarif_place
        BigDecimal tarifBase = BigDecimal.ZERO;
        if (typePlaceId != null) {
            Optional<TarifPlace> tarifPlace = tarifPlaceRepository.findAll().stream()
                    .filter(t -> t.getTypePlace() != null && t.getTypePlace().getIdTypePlace().equals(typePlaceId))
                    .findFirst();
            
            if (tarifPlace.isPresent() && tarifPlace.get().getPrix() != null) {
                tarifBase = tarifPlace.get().getPrix();
            }
        }
        
        // Si tarif de base est toujours zéro, utiliser le tarif de la séance
        if (tarifBase.compareTo(BigDecimal.ZERO) == 0) {
            Optional<Seance> seance = seanceRepository.findById(seanceId);
            tarifBase = seance.map(Seance::getTarif).orElse(BigDecimal.ZERO);
        }
        
        // Si une catégorie client est spécifiée, chercher une remise
        if (categorieClientId != null) {
            Optional<RemiseCategorie> remiseOpt = Optional.empty();
            
            // 1. Chercher d'abord une remise spécifique au type de place
            if (typePlaceId != null) {
                remiseOpt = remiseCategorieRepository.findByTypePlaceAndCategorie(typePlaceId, categorieClientId);
            }
            
            // 2. Si pas trouvé, chercher une remise générale (id_type_place = NULL)
            if (remiseOpt.isEmpty()) {
                remiseOpt = remiseCategorieRepository.findGeneralByCategorie(categorieClientId);
            }
            
            if (remiseOpt.isPresent()) {
                RemiseCategorie remise = remiseOpt.get();
                
                // Si le tarif dans remise_categorie n'est pas NULL, utiliser ce tarif
                if (remise.getTarif() != null) {
                    return remise.getTarif();
                }
                
                // Sinon, calculer avec le pourcentage de réduction
                if (remise.getPourcentageDeReduction() != null) {
                    BigDecimal reduction = tarifBase
                            .multiply(remise.getPourcentageDeReduction())
                            .divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
                    return tarifBase.subtract(reduction);
                }
            }
        }
        
        // Retourner le tarif de base si aucune remise ne s'applique
        return tarifBase;
    }

    /**
     * Crée un ticket avec les places sélectionnées (sans paiement automatique)
     * @param clientId ID du client
     * @param seanceId ID de la séance
     * @param typePlaceIds Liste des IDs de types de place ou IDs de places individuelles
     * @param categorieClientIds Liste des IDs de catégories client
     * @param nombres Liste des nombres de places pour chaque sélection (si vide, 1 par défaut)
     */
    public Ticket createAchat(Integer clientId, Integer seanceId, List<Integer> typePlaceIds, List<Integer> categorieClientIds, List<Integer> nombres) {
        // Vérifier le client
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        // Vérifier la séance
        Seance seance = seanceRepository.findById(seanceId)
                .orElseThrow(() -> new RuntimeException("Séance non trouvée"));

        // Récupérer les places occupées
        Set<Integer> occupiedPlaceIds = new HashSet<>(ticketPlaceRepository.findOccupiedPlaceIdsBySeanceId(seanceId));
        occupiedPlaceIds.addAll(reservationPlaceRepository.findOccupiedPlaceIdsBySeanceId(seanceId));
        
        // Vérifier la disponibilité AVANT l'attribution
        // Calculer le nombre de places demandées par type
        Map<Integer, Integer> placesDemandeesParType = new HashMap<>();
        for (int i = 0; i < typePlaceIds.size(); i++) {
            Integer typePlaceId = typePlaceIds.get(i);
            Integer nombre = (i < nombres.size() && nombres.get(i) != null) ? nombres.get(i) : 1;
            placesDemandeesParType.put(typePlaceId, placesDemandeesParType.getOrDefault(typePlaceId, 0) + nombre);
        }
        
        // Compter les places disponibles par type dans la salle
        for (Map.Entry<Integer, Integer> entry : placesDemandeesParType.entrySet()) {
            Integer typePlaceId = entry.getKey();
            Integer nombreDemande = entry.getValue();
            
            long nombreDisponible = placeRepository.findAll().stream()
                    .filter(p -> p.getSalle() != null && p.getSalle().getIdSalle().equals(seance.getSalle().getIdSalle()))
                    .filter(p -> p.getTypePlace() != null && p.getTypePlace().getIdTypePlace().equals(typePlaceId))
                    .filter(p -> !occupiedPlaceIds.contains(p.getIdPlace()))
                    .count();
            
            if (nombreDemande > nombreDisponible) {
                String typeName = typePlaceId == 1 ? "Standard" : (typePlaceId == 2 ? "Premium" : (typePlaceId == 3 ? "VIP" : "type " + typePlaceId));
                throw new RuntimeException("Pas assez de places disponibles de type " + typeName + 
                        ". Demandé: " + nombreDemande + ", Disponible: " + nombreDisponible);
            }
        }
        
        // Créer le ticket
        Ticket ticket = new Ticket();
        ticket.setClient(client);
        ticket.setSeance(seance);
        ticket.setDateAchat(LocalDate.now());

        // Calculer le total et créer les ticket_place
        BigDecimal total = BigDecimal.ZERO;
        ticket = ticketRepository.save(ticket);

        for (int i = 0; i < typePlaceIds.size(); i++) {
            Integer typePlaceOrPlaceId = typePlaceIds.get(i);
            Integer categorieId = (i < categorieClientIds.size()) ? categorieClientIds.get(i) : null;
            Integer nombre = (i < nombres.size() && nombres.get(i) != null) ? nombres.get(i) : 1;

            // Créer "nombre" de ticket_place pour cette sélection
            for (int j = 0; j < nombre; j++) {
                // Chercher une place disponible du bon type (attribution automatique)
                Place place = placeRepository.findAll().stream()
                        .filter(p -> p.getSalle() != null && p.getSalle().getIdSalle().equals(seance.getSalle().getIdSalle()))
                        .filter(p -> p.getTypePlace() != null && p.getTypePlace().getIdTypePlace().equals(typePlaceOrPlaceId))
                        .filter(p -> !occupiedPlaceIds.contains(p.getIdPlace()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Erreur système: Plus de places disponibles de type " + typePlaceOrPlaceId));

                // Marquer la place comme occupée
                occupiedPlaceIds.add(place.getIdPlace());

                Integer typePlaceId = place.getTypePlace() != null ? place.getTypePlace().getIdTypePlace() : null;
                BigDecimal prix = getTarif(seanceId, categorieId, typePlaceId);

                TicketPlace ticketPlace = new TicketPlace();
                ticketPlace.setTicket(ticket);
                ticketPlace.setPlace(place);
                ticketPlace.setPrix(prix);

                if (categorieId != null) {
                    categorieClientRepository.findById(categorieId).ifPresent(ticketPlace::setCategorieClient);
                }

                ticketPlaceRepository.save(ticketPlace);
                total = total.add(prix);
            }
        }

        // Mettre à jour le total du ticket
        ticket.setTotal(total);
        ticket = ticketRepository.save(ticket);

        // Créer le paiement automatiquement avec le statut PAIEMENT_ATTENTE
        Status statusAttente = statusRepository.findByCode("PAIEMENT_ATTENTE")
                .orElseThrow(() -> new RuntimeException("Statut PAIEMENT_ATTENTE non trouvé"));

        Paiement paiement = new Paiement();
        paiement.setTicket(ticket);
        paiement.setMontant(total);
        paiement.setCreerLe(LocalDate.now());
        paiement.setStatus(statusAttente);
        paiement.setDatePaiementPrevu(LocalDate.now());

        paiementRepository.save(paiement);

        return ticket;
    }

    /**
     * Récupère tous les tarifs disponibles basés sur tarif_place
     */
    public Map<String, BigDecimal> getAllTarifsForSeance(Integer seanceId) {
        // Retourner tous les tarifs de tarif_place
        return tarifPlaceRepository.findAll().stream()
                .filter(t -> t.getTypePlace() != null)
                .collect(Collectors.toMap(
                        t -> t.getTypePlace().getIdTypePlace().toString(),
                        TarifPlace::getPrix,
                        (existing, replacement) -> existing
                ));
    }

    /**
     * Récupère tous les tarifs par type de place depuis tarif_place
     */
    public List<TarifPlaceDTO> getTarifsPlaceForSeance(Integer seanceId) {
        return tarifPlaceRepository.findAll().stream()
                .map(t -> {
                    TarifPlaceDTO dto = new TarifPlaceDTO();
                    dto.setIdTarif(t.getIdTarif());
                    dto.setPrix(t.getPrix());
                    
                    if (t.getTypePlace() != null) {
                        dto.setIdTypePlace(t.getTypePlace().getIdTypePlace());
                    }
                    
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
