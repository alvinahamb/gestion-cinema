package com.example.demo.service;

import com.example.demo.dto.PlaceDTO;
import com.example.demo.dto.SalleDTO;
import com.example.demo.dto.TypePlaceDTO;
import com.example.demo.entity.Place;
import com.example.demo.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;

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
        SalleDTO salleDTO = null;
        if (place.getSalle() != null) {
            salleDTO = new SalleDTO();
            salleDTO.setIdSalle(place.getSalle().getIdSalle());
            salleDTO.setNomSalle(place.getSalle().getNomSalle());
        }
        
        TypePlaceDTO typePlaceDTO = null;
        if (place.getTypePlace() != null) {
            typePlaceDTO = new TypePlaceDTO();
            typePlaceDTO.setIdTypePlace(place.getTypePlace().getIdTypePlace());
            typePlaceDTO.setLibelle(place.getTypePlace().getLibelle());
        }
        
        return new PlaceDTO(
            place.getIdPlace(),
            salleDTO,
            typePlaceDTO,
            place.getNumeroLigne(),
            place.getNumeroColonne()
        );
    }

    private Place convertToEntity(PlaceDTO placeDTO) {
        Place place = new Place();
        place.setNumeroLigne(placeDTO.getNumeroLigne());
        place.setNumeroColonne(placeDTO.getNumeroColonne());
        return place;
    }
}
