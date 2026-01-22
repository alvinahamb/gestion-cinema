package com.example.demo.service;

import com.example.demo.dto.SocieteDTO;
import com.example.demo.entity.Societe;
import com.example.demo.repository.SocieteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocieteService {
    @Autowired
    private SocieteRepository societeRepository;

    public List<SocieteDTO> getAllSocietes() {
        return societeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<SocieteDTO> getSocieteById(Integer id) {
        return societeRepository.findById(id)
                .map(this::convertToDTO);
    }

    public SocieteDTO createSociete(SocieteDTO societeDTO) {
        Societe societe = convertToEntity(societeDTO);
        if (societe.getDateAjout() == null) {
            societe.setDateAjout(LocalDate.now());
        }
        Societe savedSociete = societeRepository.save(societe);
        return convertToDTO(savedSociete);
    }

    public SocieteDTO updateSociete(Integer id, SocieteDTO societeDTO) {
        Optional<Societe> existingSociete = societeRepository.findById(id);
        if (existingSociete.isPresent()) {
            Societe societe = existingSociete.get();
            societe.setNom(societeDTO.getNom());
            societe.setTelephone(societeDTO.getTelephone());
            societe.setAdresse(societeDTO.getAdresse());
            Societe updatedSociete = societeRepository.save(societe);
            return convertToDTO(updatedSociete);
        }
        return null;
    }

    public boolean deleteSociete(Integer id) {
        if (societeRepository.existsById(id)) {
            societeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private SocieteDTO convertToDTO(Societe societe) {
        return new SocieteDTO(
            societe.getId(),
            societe.getNom(),
            societe.getDateAjout(),
            societe.getTelephone(),
            societe.getAdresse()
        );
    }

    private Societe convertToEntity(SocieteDTO dto) {
        return new Societe(
            dto.getNom(),
            dto.getDateAjout(),
            dto.getTelephone(),
            dto.getAdresse()
        );
    }
}
