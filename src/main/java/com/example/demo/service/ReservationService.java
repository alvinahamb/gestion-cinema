package com.example.demo.service;

import com.example.demo.dto.CategorieClientDTO;
import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.ReservationDTO;
import com.example.demo.dto.ReservationPlaceDTO;
import com.example.demo.dto.SeanceDTO;
import com.example.demo.dto.StatusDTO;
import com.example.demo.dto.TypeStatutDTO;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.ReservationPlace;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.ReservationPlaceRepository;
import com.example.demo.dto.PlaceDTO;
import com.example.demo.entity.Place;
import com.example.demo.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private ReservationPlaceRepository reservationPlaceRepository;
    
    @Autowired
    private PlaceRepository placeRepository;

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ReservationDTO> getReservationById(Integer id) {
        return reservationRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = convertToEntity(reservationDTO);
        Reservation savedReservation = reservationRepository.save(reservation);
        return convertToDTO(savedReservation);
    }

    public ReservationDTO updateReservation(Integer id, ReservationDTO reservationDTO) {
        Optional<Reservation> existingReservation = reservationRepository.findById(id);
        if (existingReservation.isPresent()) {
            Reservation reservation = existingReservation.get();
            reservation.setDateReservation(reservationDTO.getDateReservation());
            reservation.setTotal(reservationDTO.getTotal());
            reservation.setRemise(reservationDTO.getRemise());
            
            Reservation updatedReservation = reservationRepository.save(reservation);
            return convertToDTO(updatedReservation);
        }
        return null;
    }

    public boolean deleteReservation(Integer id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ========== Reservation Places Management ==========
    public List<ReservationPlaceDTO> getReservationPlacesByReservationId(Integer reservationId) {
        return reservationPlaceRepository.findAll().stream()
                .filter(rp -> rp.getReservation() != null && rp.getReservation().getIdReservation().equals(reservationId))
                .map(this::convertReservationPlaceToDTO)
                .collect(Collectors.toList());
    }

    public ReservationPlaceDTO addPlaceToReservation(Integer reservationId, Integer placeId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        Optional<Place> place = placeRepository.findById(placeId);
        
        if (reservation.isPresent() && place.isPresent()) {
            ReservationPlace reservationPlace = new ReservationPlace();
            reservationPlace.setReservation(reservation.get());
            reservationPlace.setPlace(place.get());
            ReservationPlace saved = reservationPlaceRepository.save(reservationPlace);
            return convertReservationPlaceToDTO(saved);
        }
        return null;
    }

    public boolean removePlaceFromReservation(Integer reservationPlaceId) {
        if (reservationPlaceRepository.existsById(reservationPlaceId)) {
            reservationPlaceRepository.deleteById(reservationPlaceId);
            return true;
        }
        return false;
    }

    private ReservationPlaceDTO convertReservationPlaceToDTO(ReservationPlace rp) {
        ReservationPlaceDTO dto = new ReservationPlaceDTO();
        dto.setId(rp.getId());
        if (rp.getPlace() != null) {
            PlaceDTO placeDTO = new PlaceDTO();
            placeDTO.setIdPlace(rp.getPlace().getIdPlace());
            placeDTO.setNumeroLigne(rp.getPlace().getNumeroLigne());
            placeDTO.setNumeroColonne(rp.getPlace().getNumeroColonne());
            dto.setPlace(placeDTO);
        }
        return dto;
    }
    private ReservationDTO convertToDTO(Reservation reservation) {
        ClientDTO clientDTO = null;
        if (reservation.getClient() != null) {
            CategorieClientDTO categorieClientDTO = null;
            if (reservation.getClient().getCategorieClient() != null) {
                categorieClientDTO = new CategorieClientDTO(
                    reservation.getClient().getCategorieClient().getIdCategorieClient(),
                    reservation.getClient().getCategorieClient().getLibelle(),
                    reservation.getClient().getCategorieClient().getDescription()
                );
            }
            clientDTO = new ClientDTO(reservation.getClient().getIdClient(),
                                     reservation.getClient().getNom(),
                                     reservation.getClient().getPrenom(),
                                     reservation.getClient().getDateNaissance(),
                                     reservation.getClient().getEmail(),
                                     reservation.getClient().getTelephone(),
                                     categorieClientDTO);
        }
        
        SeanceDTO seanceDTO = null;
        if (reservation.getSeance() != null) {
            seanceDTO = new SeanceDTO();
            seanceDTO.setIdSeance(reservation.getSeance().getIdSeance());
        }
        
        StatusDTO statusDTO = null;
        if (reservation.getStatus() != null) {
            TypeStatutDTO typeStatutDTO = new TypeStatutDTO();
            typeStatutDTO.setIdTypeStatut(reservation.getStatus().getTypeStatut().getIdTypeStatut());
            typeStatutDTO.setLibelle(reservation.getStatus().getTypeStatut().getLibelle());
            
            statusDTO = new StatusDTO(
                reservation.getStatus().getIdStatus(),
                reservation.getStatus().getLibelle(),
                reservation.getStatus().getCode(),
                reservation.getStatus().getOrdre(),
                typeStatutDTO
            );
        }
        
        return new ReservationDTO(reservation.getIdReservation(),
                                 clientDTO,
                                 seanceDTO,
                                 reservation.getDateReservation(),
                                 reservation.getTotal(),
                                 reservation.getRemise(),
                                 statusDTO,
                                 reservation.getDatePaiementPrevu());
    }

    private Reservation convertToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setDateReservation(reservationDTO.getDateReservation());
        reservation.setTotal(reservationDTO.getTotal());
        reservation.setRemise(reservationDTO.getRemise());
        reservation.setDatePaiementPrevu(reservationDTO.getDatePaiementPrevu());
        return reservation;
    }
}
