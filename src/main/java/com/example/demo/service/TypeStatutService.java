package com.example.demo.service;

import com.example.demo.dto.TypeStatutDTO;
import com.example.demo.entity.TypeStatut;
import com.example.demo.repository.TypeStatutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TypeStatutService {
    @Autowired
    private TypeStatutRepository typeStatutRepository;

    public List<TypeStatutDTO> getAllTypeStatuts() {
        return typeStatutRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TypeStatutDTO> getTypeStatutById(Integer id) {
        return typeStatutRepository.findById(id)
                .map(this::convertToDTO);
    }

    public TypeStatutDTO createTypeStatut(TypeStatutDTO typeStatutDTO) {
        TypeStatut typeStatut = convertToEntity(typeStatutDTO);
        TypeStatut savedTypeStatut = typeStatutRepository.save(typeStatut);
        return convertToDTO(savedTypeStatut);
    }

    public TypeStatutDTO updateTypeStatut(Integer id, TypeStatutDTO typeStatutDTO) {
        Optional<TypeStatut> existingTypeStatut = typeStatutRepository.findById(id);
        if (existingTypeStatut.isPresent()) {
            TypeStatut typeStatut = existingTypeStatut.get();
            typeStatut.setLibelle(typeStatutDTO.getLibelle());
            TypeStatut updatedTypeStatut = typeStatutRepository.save(typeStatut);
            return convertToDTO(updatedTypeStatut);
        }
        return null;
    }

    public boolean deleteTypeStatut(Integer id) {
        if (typeStatutRepository.existsById(id)) {
            typeStatutRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private TypeStatutDTO convertToDTO(TypeStatut typeStatut) {
        return new TypeStatutDTO(typeStatut.getIdTypeStatut(), typeStatut.getLibelle());
    }

    private TypeStatut convertToEntity(TypeStatutDTO typeStatutDTO) {
        return new TypeStatut(typeStatutDTO.getLibelle());
    }
}