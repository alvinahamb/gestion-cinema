package com.example.demo.service;

import com.example.demo.dto.PaiementSocieteDTO;
import com.example.demo.entity.PaiementSociete;
import com.example.demo.entity.Diffusion;
import com.example.demo.repository.PaiementSocieteRepository;
import com.example.demo.repository.DiffusionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaiementSocieteService {
    @Autowired
    private PaiementSocieteRepository paiementSocieteRepository;
    
    @Autowired
    private DiffusionRepository diffusionRepository;

    public List<PaiementSocieteDTO> getAllPaiementsSociete() {
        return paiementSocieteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PaiementSocieteDTO> getPaiementsByDiffusionId(Integer diffusionId) {
        return paiementSocieteRepository.findByDiffusionId(diffusionId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PaiementSocieteDTO> getPaiementSocieteById(Integer id) {
        return paiementSocieteRepository.findById(id)
                .map(this::convertToDTO);
    }

    public PaiementSocieteDTO createPaiementSociete(PaiementSocieteDTO dto) {
        PaiementSociete paiement = new PaiementSociete();
        
        if (dto.getIdDiffusion() != null) {
            diffusionRepository.findById(dto.getIdDiffusion())
                .ifPresent(paiement::setDiffusion);
        }
        
        paiement.setMontant(dto.getMontant());
        paiement.setDatePaiement(dto.getDatePaiement() != null ? dto.getDatePaiement() : LocalDate.now());
        
        PaiementSociete saved = paiementSocieteRepository.save(paiement);
        return convertToDTO(saved);
    }

    public PaiementSocieteDTO updatePaiementSociete(Integer id, PaiementSocieteDTO dto) {
        Optional<PaiementSociete> existing = paiementSocieteRepository.findById(id);
        if (existing.isPresent()) {
            PaiementSociete paiement = existing.get();
            
            if (dto.getIdDiffusion() != null) {
                diffusionRepository.findById(dto.getIdDiffusion())
                    .ifPresent(paiement::setDiffusion);
            }
            
            paiement.setMontant(dto.getMontant());
            paiement.setDatePaiement(dto.getDatePaiement());
            
            PaiementSociete updated = paiementSocieteRepository.save(paiement);
            return convertToDTO(updated);
        }
        return null;
    }

    public boolean deletePaiementSociete(Integer id) {
        if (paiementSocieteRepository.existsById(id)) {
            paiementSocieteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public BigDecimal getSommePaiementsByDiffusionId(Integer diffusionId) {
        return paiementSocieteRepository.sumMontantByDiffusionId(diffusionId);
    }

    public BigDecimal getMontantRestantByDiffusionId(Integer diffusionId) {
        Optional<Diffusion> diffusion = diffusionRepository.findById(diffusionId);
        if (diffusion.isPresent() && diffusion.get().getTarifDiffusion() != null 
            && diffusion.get().getTarifDiffusion().getTarif() != null
            && diffusion.get().getNombre() != null) {
            
            BigDecimal montantTotal = diffusion.get().getTarifDiffusion().getTarif()
                .multiply(BigDecimal.valueOf(diffusion.get().getNombre()));
            BigDecimal sommePaiements = getSommePaiementsByDiffusionId(diffusionId);
            return montantTotal.subtract(sommePaiements);
        }
        return BigDecimal.ZERO;
    }

    private PaiementSocieteDTO convertToDTO(PaiementSociete paiement) {
        PaiementSocieteDTO dto = new PaiementSocieteDTO();
        dto.setId(paiement.getId());
        dto.setMontant(paiement.getMontant());
        dto.setDatePaiement(paiement.getDatePaiement());
        
        if (paiement.getDiffusion() != null) {
            dto.setIdDiffusion(paiement.getDiffusion().getId());
            String societeNom = paiement.getDiffusion().getSociete() != null 
                ? paiement.getDiffusion().getSociete().getNom() : "";
            String moisAnnee = paiement.getDiffusion().getMois() + "/" + paiement.getDiffusion().getAnnee();
            dto.setDiffusionInfo(societeNom + " - " + moisAnnee);
        }
        
        return dto;
    }
}
