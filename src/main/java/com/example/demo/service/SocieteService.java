package com.example.demo.service;

import com.example.demo.dto.FactureSocieteDTO;
import com.example.demo.dto.SocieteDTO;
import com.example.demo.entity.Societe;
import com.example.demo.repository.SocieteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocieteService {
    @Autowired
    private SocieteRepository societeRepository;

    @Autowired
    private FactureSocieteService factureSocieteService;

    public List<SocieteDTO> getAllSocietes() {
        return societeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SocieteDTO> getAllSocietesWithFactures() {
        return societeRepository.findAll().stream()
                .map(this::convertToDTOWithFactures)
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
        SocieteDTO dto = new SocieteDTO();
        dto.setId(societe.getId());
        dto.setNom(societe.getNom());
        dto.setDateAjout(societe.getDateAjout());
        dto.setTelephone(societe.getTelephone());
        dto.setAdresse(societe.getAdresse());
        return dto;
    }

    private SocieteDTO convertToDTOWithFactures(Societe societe) {
        SocieteDTO dto = convertToDTO(societe);
        
        // Récupérer les factures de cette société
        List<FactureSocieteDTO> factures = factureSocieteService.getFacturesBySocieteId(societe.getId());
        dto.setFactures(factures);
        
        // Calculer les totaux
        BigDecimal totalFactures = factures.stream()
                .map(f -> f.getTotal() != null ? f.getTotal() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotalFactures(totalFactures);
        
        BigDecimal totalPaye = factures.stream()
                .map(f -> f.getMontantPaye() != null ? f.getMontantPaye() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotalPaye(totalPaye);
        
        dto.setTotalRestant(totalFactures.subtract(totalPaye));
        
        return dto;
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
