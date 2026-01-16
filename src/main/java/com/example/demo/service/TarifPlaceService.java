package com.example.demo.service;

import com.example.demo.dto.TarifPlaceDTO;
import com.example.demo.entity.TarifPlace;
import com.example.demo.entity.TypePlace;
import com.example.demo.repository.TarifPlaceRepository;
import com.example.demo.repository.TypePlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarifPlaceService {
    @Autowired
    private TarifPlaceRepository tarifPlaceRepository;

    @Autowired
    private TypePlaceRepository typePlaceRepository;

    public List<TarifPlaceDTO> getAllTarifPlaces() {
        return tarifPlaceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TarifPlaceDTO> getTarifPlaceById(Integer id) {
        return tarifPlaceRepository.findById(id)
                .map(this::convertToDTO);
    }

    public TarifPlaceDTO createTarifPlace(TarifPlaceDTO tarifPlaceDTO) {
        TarifPlace tarifPlace = convertToEntity(tarifPlaceDTO);
        TarifPlace savedTarifPlace = tarifPlaceRepository.save(tarifPlace);
        return convertToDTO(savedTarifPlace);
    }

    public TarifPlaceDTO updateTarifPlace(Integer id, TarifPlaceDTO tarifPlaceDTO) {
        Optional<TarifPlace> existingTarifPlace = tarifPlaceRepository.findById(id);
        if (existingTarifPlace.isPresent()) {
            TarifPlace tarifPlace = existingTarifPlace.get();
            tarifPlace.setPrix(tarifPlaceDTO.getPrix());
            // Note: idTypePlace is not updated, as it's the key
            TarifPlace updatedTarifPlace = tarifPlaceRepository.save(tarifPlace);
            return convertToDTO(updatedTarifPlace);
        }
        return null;
    }

    public Optional<TarifPlaceDTO> getTarifPlaceByTypePlaceId(Integer idTypePlace) {
        return tarifPlaceRepository.findAll().stream()
                .filter(tp -> tp.getTypePlace().getIdTypePlace().equals(idTypePlace))
                .findFirst()
                .map(this::convertToDTO);
    }

    public boolean deleteTarifPlace(Integer id) {
        if (tarifPlaceRepository.existsById(id)) {
            tarifPlaceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private TarifPlaceDTO convertToDTO(TarifPlace tarifPlace) {
        return new TarifPlaceDTO(
            tarifPlace.getIdTarif(),
            tarifPlace.getTypePlace().getIdTypePlace(),
            tarifPlace.getPrix()
        );
    }

    private TarifPlace convertToEntity(TarifPlaceDTO tarifPlaceDTO) {
        Optional<TypePlace> typePlace = typePlaceRepository.findById(tarifPlaceDTO.getIdTypePlace());
        if (typePlace.isPresent()) {
            TarifPlace tarifPlace = new TarifPlace(typePlace.get(), tarifPlaceDTO.getPrix());
            if (tarifPlaceDTO.getIdTarif() != null) {
                tarifPlace.setIdTarif(tarifPlaceDTO.getIdTarif());
            }
            return tarifPlace;
        }
        return null;
    }
}