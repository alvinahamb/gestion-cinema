package com.example.demo.service;

import com.example.demo.dto.CategorieClientDTO;
import com.example.demo.dto.PlaceDTO;
import com.example.demo.dto.ReservationDTO;
import com.example.demo.dto.ReservationPlaceDTO;
import com.example.demo.dto.SalleDTO;
import com.example.demo.dto.TypePlaceDTO;
import com.example.demo.entity.CategorieClient;
import com.example.demo.entity.Place;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.ReservationPlace;
import com.example.demo.repository.CategorieClientRepository;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.repository.ReservationPlaceRepository;
import com.example.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationPlaceService {
    @Autowired
    private ReservationPlaceRepository reservationPlaceRepository;
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private PlaceRepository placeRepository;
    
    @Autowired
    private CategorieClientRepository categorieClientRepository;

    public List<ReservationPlaceDTO> getAllReservationPlaces() {
        return reservationPlaceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ReservationPlaceDTO> getReservationPlaceById(Integer id) {
        return reservationPlaceRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<ReservationPlaceDTO> getByReservationId(Integer reservationId) {
        return reservationPlaceRepository.findAll().stream()
                .filter(rp -> rp.getReservation() != null && rp.getReservation().getIdReservation().equals(reservationId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReservationPlaceDTO createReservationPlace(ReservationPlaceDTO dto) {
        ReservationPlace entity = convertToEntity(dto);
        ReservationPlace saved = reservationPlaceRepository.save(entity);
        return convertToDTO(saved);
    }

    public ReservationPlaceDTO updateReservationPlace(Integer id, ReservationPlaceDTO dto) {
        Optional<ReservationPlace> existing = reservationPlaceRepository.findById(id);
        if (existing.isPresent()) {
            ReservationPlace entity = existing.get();
            
            if (dto.getReservation() != null && dto.getReservation().getIdReservation() != null) {
                reservationRepository.findById(dto.getReservation().getIdReservation())
                    .ifPresent(entity::setReservation);
            }
            
            if (dto.getPlace() != null && dto.getPlace().getIdPlace() != null) {
                placeRepository.findById(dto.getPlace().getIdPlace())
                    .ifPresent(entity::setPlace);
            }
            
            if (dto.getCategorieClient() != null && dto.getCategorieClient().getIdCategorieClient() != null) {
                categorieClientRepository.findById(dto.getCategorieClient().getIdCategorieClient())
                    .ifPresent(entity::setCategorieClient);
            }
            
            entity.setPrix(dto.getPrix());
            ReservationPlace updated = reservationPlaceRepository.save(entity);
            return convertToDTO(updated);
        }
        return null;
    }

    public boolean deleteReservationPlace(Integer id) {
        if (reservationPlaceRepository.existsById(id)) {
            reservationPlaceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ReservationPlaceDTO convertToDTO(ReservationPlace entity) {
        ReservationDTO reservationDTO = null;
        if (entity.getReservation() != null) {
            reservationDTO = new ReservationDTO();
            reservationDTO.setIdReservation(entity.getReservation().getIdReservation());
        }
        
        PlaceDTO placeDTO = null;
        if (entity.getPlace() != null) {
            Place place = entity.getPlace();
            SalleDTO salleDTO = null;
            if (place.getSalle() != null) {
                salleDTO = new SalleDTO(place.getSalle().getIdSalle(), place.getSalle().getNomSalle(), 
                                       place.getSalle().getCapacite(), place.getSalle().getLigne(), 
                                       place.getSalle().getColonne());
            }
            TypePlaceDTO typePlaceDTO = null;
            if (place.getTypePlace() != null) {
                typePlaceDTO = new TypePlaceDTO(place.getTypePlace().getIdTypePlace(), 
                                               place.getTypePlace().getLibelle(), 
                                               place.getTypePlace().getDescription());
            }
            placeDTO = new PlaceDTO(place.getIdPlace(), salleDTO, typePlaceDTO, 
                                   place.getNumeroLigne(), place.getNumeroColonne());
        }
        
        CategorieClientDTO categorieClientDTO = null;
        if (entity.getCategorieClient() != null) {
            CategorieClient cc = entity.getCategorieClient();
            categorieClientDTO = new CategorieClientDTO(cc.getIdCategorieClient(), 
                                                        cc.getLibelle(), cc.getDescription());
        }
        
        return new ReservationPlaceDTO(
            entity.getId(),
            reservationDTO,
            placeDTO,
            categorieClientDTO,
            entity.getPrix()
        );
    }

    private ReservationPlace convertToEntity(ReservationPlaceDTO dto) {
        Reservation reservation = null;
        if (dto.getReservation() != null && dto.getReservation().getIdReservation() != null) {
            reservation = reservationRepository.findById(dto.getReservation().getIdReservation()).orElse(null);
        }
        
        Place place = null;
        if (dto.getPlace() != null && dto.getPlace().getIdPlace() != null) {
            place = placeRepository.findById(dto.getPlace().getIdPlace()).orElse(null);
        }
        
        CategorieClient categorieClient = null;
        if (dto.getCategorieClient() != null && dto.getCategorieClient().getIdCategorieClient() != null) {
            categorieClient = categorieClientRepository.findById(dto.getCategorieClient().getIdCategorieClient()).orElse(null);
        }
        
        return new ReservationPlace(reservation, place, categorieClient, dto.getPrix());
    }
}
