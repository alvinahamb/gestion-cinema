package com.example.demo.service;

import com.example.demo.dto.DiffusionDTO;
import com.example.demo.entity.Diffusion;
import com.example.demo.entity.Publicite;
import com.example.demo.entity.Societe;
import com.example.demo.entity.TarifDiffusion;
import com.example.demo.repository.DiffusionRepository;
import com.example.demo.repository.PaiementSocieteRepository;
import com.example.demo.repository.PubliciteRepository;
import com.example.demo.repository.SocieteRepository;
import com.example.demo.repository.TarifDiffusionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiffusionService {
    @Autowired
    private DiffusionRepository diffusionRepository;
    
    @Autowired
    private SocieteRepository societeRepository;
    
    @Autowired
    private PubliciteRepository publiciteRepository;
    
    @Autowired
    private TarifDiffusionRepository tarifDiffusionRepository;
    
    @Autowired
    private PaiementSocieteRepository paiementSocieteRepository;

    public List<DiffusionDTO> getAllDiffusions() {
        return diffusionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<DiffusionDTO> getDiffusionById(Integer id) {
        return diffusionRepository.findById(id)
                .map(this::convertToDTO);
    }

    public DiffusionDTO createDiffusion(DiffusionDTO diffusionDTO) {
        Diffusion diffusion = new Diffusion();
        
        // Récupérer les entités liées
        if (diffusionDTO.getIdSociete() != null) {
            societeRepository.findById(diffusionDTO.getIdSociete())
                .ifPresent(diffusion::setSociete);
        }
        if (diffusionDTO.getIdPublicite() != null) {
            publiciteRepository.findById(diffusionDTO.getIdPublicite())
                .ifPresent(diffusion::setPublicite);
        }
        if (diffusionDTO.getIdTarif() != null) {
            tarifDiffusionRepository.findById(diffusionDTO.getIdTarif())
                .ifPresent(diffusion::setTarifDiffusion);
        }
        
        diffusion.setMois(diffusionDTO.getMois());
        diffusion.setAnnee(diffusionDTO.getAnnee());
        
        // date_diffusion = 01/mois/annee
        if (diffusionDTO.getMois() != null && diffusionDTO.getAnnee() != null) {
            diffusion.setDateDiffusion(LocalDate.of(diffusionDTO.getAnnee(), diffusionDTO.getMois(), 1));
        }
        
        diffusion.setNombre(diffusionDTO.getNombre());
        
        // diff_attribue = 0, diff_restant = nombre
        diffusion.setDiffAttribue(0);
        diffusion.setDiffRestant(diffusionDTO.getNombre());
        
        diffusion.setDateAjout(LocalDate.now());
        
        Diffusion savedDiffusion = diffusionRepository.save(diffusion);
        return convertToDTO(savedDiffusion);
    }

    public DiffusionDTO updateDiffusion(Integer id, DiffusionDTO diffusionDTO) {
        Optional<Diffusion> existingDiffusion = diffusionRepository.findById(id);
        if (existingDiffusion.isPresent()) {
            Diffusion diffusion = existingDiffusion.get();
            
            if (diffusionDTO.getIdSociete() != null) {
                societeRepository.findById(diffusionDTO.getIdSociete())
                    .ifPresent(diffusion::setSociete);
            }
            if (diffusionDTO.getIdPublicite() != null) {
                publiciteRepository.findById(diffusionDTO.getIdPublicite())
                    .ifPresent(diffusion::setPublicite);
            }
            if (diffusionDTO.getIdTarif() != null) {
                tarifDiffusionRepository.findById(diffusionDTO.getIdTarif())
                    .ifPresent(diffusion::setTarifDiffusion);
            }
            
            diffusion.setMois(diffusionDTO.getMois());
            diffusion.setAnnee(diffusionDTO.getAnnee());
            
            // Recalculer date_diffusion si mois/annee changés
            if (diffusionDTO.getMois() != null && diffusionDTO.getAnnee() != null) {
                diffusion.setDateDiffusion(LocalDate.of(diffusionDTO.getAnnee(), diffusionDTO.getMois(), 1));
            }
            
            // Recalculer diff_restant si nombre change
            Integer oldNombre = diffusion.getNombre() != null ? diffusion.getNombre() : 0;
            Integer newNombre = diffusionDTO.getNombre() != null ? diffusionDTO.getNombre() : 0;
            Integer diffAttribue = diffusion.getDiffAttribue() != null ? diffusion.getDiffAttribue() : 0;
            
            diffusion.setNombre(newNombre);
            diffusion.setDiffRestant(newNombre - diffAttribue);
            
            Diffusion updatedDiffusion = diffusionRepository.save(diffusion);
            return convertToDTO(updatedDiffusion);
        }
        return null;
    }

    public boolean deleteDiffusion(Integer id) {
        if (diffusionRepository.existsById(id)) {
            diffusionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public java.math.BigDecimal getTotalTarifsByMoisAnnee(Integer mois, Integer annee) {
        List<Diffusion> diffusions = diffusionRepository.findByMoisAndAnnee(mois, annee);
        return diffusions.stream()
            .filter(d -> d.getTarifDiffusion() != null && d.getTarifDiffusion().getTarif() != null && d.getNombre() != null)
            .map(d -> d.getTarifDiffusion().getTarif().multiply(java.math.BigDecimal.valueOf(d.getNombre())))
            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }

    private DiffusionDTO convertToDTO(Diffusion diffusion) {
        DiffusionDTO dto = new DiffusionDTO();
        dto.setId(diffusion.getId());
        
        if (diffusion.getSociete() != null) {
            dto.setIdSociete(diffusion.getSociete().getId());
            dto.setNomSociete(diffusion.getSociete().getNom());
        }
        if (diffusion.getPublicite() != null) {
            dto.setIdPublicite(diffusion.getPublicite().getId());
            dto.setTitrePublicite(diffusion.getPublicite().getTitre());
        }
        if (diffusion.getTarifDiffusion() != null) {
            dto.setIdTarif(diffusion.getTarifDiffusion().getId());
            dto.setTarifLibelle(diffusion.getTarifDiffusion().getTarif() != null ? 
                diffusion.getTarifDiffusion().getTarif().toString() + " Ar" : "");
        }
        
        dto.setMois(diffusion.getMois());
        dto.setAnnee(diffusion.getAnnee());
        dto.setDateDiffusion(diffusion.getDateDiffusion());
        dto.setNombre(diffusion.getNombre());
        dto.setDiffAttribue(diffusion.getDiffAttribue());
        dto.setDiffRestant(diffusion.getDiffRestant());
        dto.setDateAjout(diffusion.getDateAjout());
        
        // Calcul du montant total et du montant restant à payer
        if (diffusion.getTarifDiffusion() != null && diffusion.getTarifDiffusion().getTarif() != null 
            && diffusion.getNombre() != null) {
            BigDecimal montantTotal = diffusion.getTarifDiffusion().getTarif()
                .multiply(BigDecimal.valueOf(diffusion.getNombre()));
            dto.setMontantTotal(montantTotal);
            
            BigDecimal sommePaiements = paiementSocieteRepository.sumMontantByDiffusionId(diffusion.getId());
            dto.setMontantRestant(montantTotal.subtract(sommePaiements));
        } else {
            dto.setMontantTotal(BigDecimal.ZERO);
            dto.setMontantRestant(BigDecimal.ZERO);
        }
        
        return dto;
    }
}
