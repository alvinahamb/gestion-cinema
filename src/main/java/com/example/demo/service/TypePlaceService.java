package com.example.demo.service;

import com.example.demo.dto.TypePlaceDTO;
import com.example.demo.entity.TypePlace;
import com.example.demo.repository.TypePlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TypePlaceService {
    @Autowired
    private TypePlaceRepository typePlaceRepository;

    public List<TypePlaceDTO> getAllTypePlaces() {
        return typePlaceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TypePlaceDTO> getTypePlaceById(Integer id) {
        return typePlaceRepository.findById(id)
                .map(this::convertToDTO);
    }

    public TypePlaceDTO createTypePlace(TypePlaceDTO typePlaceDTO) {
        TypePlace typePlace = convertToEntity(typePlaceDTO);
        TypePlace savedTypePlace = typePlaceRepository.save(typePlace);
        return convertToDTO(savedTypePlace);
    }

    public TypePlaceDTO updateTypePlace(Integer id, TypePlaceDTO typePlaceDTO) {
        Optional<TypePlace> existingTypePlace = typePlaceRepository.findById(id);
        if (existingTypePlace.isPresent()) {
            TypePlace typePlace = existingTypePlace.get();
            typePlace.setLibelle(typePlaceDTO.getLibelle());
            typePlace.setDescription(typePlaceDTO.getDescription());
            TypePlace updatedTypePlace = typePlaceRepository.save(typePlace);
            return convertToDTO(updatedTypePlace);
        }
        return null;
    }

    public boolean deleteTypePlace(Integer id) {
        if (typePlaceRepository.existsById(id)) {
            typePlaceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private TypePlaceDTO convertToDTO(TypePlace typePlace) {
        return new TypePlaceDTO(typePlace.getIdTypePlace(), typePlace.getLibelle(), typePlace.getDescription());
    }

    private TypePlace convertToEntity(TypePlaceDTO typePlaceDTO) {
        return new TypePlace(typePlaceDTO.getLibelle(), typePlaceDTO.getDescription());
    }
}
