package com.example.demo.service;

import com.example.demo.dto.PlaceDTO;
import com.example.demo.dto.TarifPlaceDTO;
import com.example.demo.entity.Place;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.repository.SalleRepository;
import com.example.demo.repository.TypePlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private TarifPlaceService tarifPlaceService;

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private TypePlaceRepository typePlaceRepository;

    public List<PlaceDTO> getAllPlaces() {
        return placeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PlaceDTO> getPlaceById(Integer id) {
        return placeRepository.findById(id)
                .map(this::convertToDTO);
    }

    public PlaceDTO createPlace(PlaceDTO placeDTO) {
        Place place = convertToEntity(placeDTO);
        Place savedPlace = placeRepository.save(place);
        return convertToDTO(savedPlace);
    }

    public PlaceDTO updatePlace(Integer id, PlaceDTO placeDTO) {
        Optional<Place> existingPlace = placeRepository.findById(id);
        if (existingPlace.isPresent()) {
            Place place = existingPlace.get();
            place.setNumeroLigne(placeDTO.getNumeroLigne());
            place.setNumeroColonne(placeDTO.getNumeroColonne());
            
            Place updatedPlace = placeRepository.save(place);
            return convertToDTO(updatedPlace);
        }
        return null;
    }

    public boolean deletePlace(Integer id) {
        if (placeRepository.existsById(id)) {
            placeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PlaceDTO convertToDTO(Place place) {
        BigDecimal prixPlace = tarifPlaceService.getTarifPlaceByTypePlaceId(place.getTypePlace().getIdTypePlace())
                .map(TarifPlaceDTO::getPrix)
                .orElse(BigDecimal.ZERO);
        
        return new PlaceDTO(
            place.getIdPlace(),
            place.getSalle().getIdSalle(),
            place.getTypePlace().getIdTypePlace(),
            place.getNumeroLigne(),
            place.getNumeroColonne(),
            prixPlace
        );
    }

    private Place convertToEntity(PlaceDTO placeDTO) {
        Place place = new Place();
        place.setNumeroLigne(placeDTO.getNumeroLigne());
        place.setNumeroColonne(placeDTO.getNumeroColonne());
        
        if (placeDTO.getIdSalle() != null) {
            salleRepository.findById(placeDTO.getIdSalle()).ifPresent(place::setSalle);
        }
        if (placeDTO.getIdTypePlace() != null) {
            typePlaceRepository.findById(placeDTO.getIdTypePlace()).ifPresent(place::setTypePlace);
        }
        
        return place;
    }
}
