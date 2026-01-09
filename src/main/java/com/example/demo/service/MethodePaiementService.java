package com.example.demo.service;

import com.example.demo.dto.MethodePaiementDTO;
import com.example.demo.entity.MethodePaiement;
import com.example.demo.repository.MethodePaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MethodePaiementService {
    @Autowired
    private MethodePaiementRepository methodePaiementRepository;

    public List<MethodePaiementDTO> getAllMethodesPaiement() {
        return methodePaiementRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<MethodePaiementDTO> getMethodePaiementById(Integer id) {
        return methodePaiementRepository.findById(id)
                .map(this::convertToDTO);
    }

    public MethodePaiementDTO createMethodePaiement(MethodePaiementDTO methodePaiementDTO) {
        MethodePaiement methodePaiement = convertToEntity(methodePaiementDTO);
        MethodePaiement savedMethodePaiement = methodePaiementRepository.save(methodePaiement);
        return convertToDTO(savedMethodePaiement);
    }

    public MethodePaiementDTO updateMethodePaiement(Integer id, MethodePaiementDTO methodePaiementDTO) {
        Optional<MethodePaiement> existingMethodePaiement = methodePaiementRepository.findById(id);
        if (existingMethodePaiement.isPresent()) {
            MethodePaiement methodePaiement = existingMethodePaiement.get();
            methodePaiement.setLibelle(methodePaiementDTO.getLibelle());
            MethodePaiement updatedMethodePaiement = methodePaiementRepository.save(methodePaiement);
            return convertToDTO(updatedMethodePaiement);
        }
        return null;
    }

    public boolean deleteMethodePaiement(Integer id) {
        if (methodePaiementRepository.existsById(id)) {
            methodePaiementRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private MethodePaiementDTO convertToDTO(MethodePaiement methodePaiement) {
        return new MethodePaiementDTO(methodePaiement.getIdMethodePaiement(), methodePaiement.getLibelle());
    }

    private MethodePaiement convertToEntity(MethodePaiementDTO methodePaiementDTO) {
        return new MethodePaiement(methodePaiementDTO.getLibelle());
    }
}
