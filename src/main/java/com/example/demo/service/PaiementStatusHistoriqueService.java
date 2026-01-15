package com.example.demo.service;

import com.example.demo.dto.PaiementDTO;
import com.example.demo.dto.PaiementStatusHistoriqueDTO;
import com.example.demo.dto.StatusDTO;
import com.example.demo.entity.Paiement;
import com.example.demo.entity.PaiementStatusHistorique;
import com.example.demo.entity.Status;
import com.example.demo.repository.PaiementRepository;
import com.example.demo.repository.PaiementStatusHistoriqueRepository;
import com.example.demo.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaiementStatusHistoriqueService {
    @Autowired
    private PaiementStatusHistoriqueRepository paiementStatusHistoriqueRepository;
    
    @Autowired
    private PaiementRepository paiementRepository;
    
    @Autowired
    private StatusRepository statusRepository;

    public List<PaiementStatusHistoriqueDTO> getAllPaiementStatusHistoriques() {
        return paiementStatusHistoriqueRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PaiementStatusHistoriqueDTO> getPaiementStatusHistoriqueById(Integer id) {
        return paiementStatusHistoriqueRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<PaiementStatusHistoriqueDTO> getByPaiementId(Integer paiementId) {
        return paiementStatusHistoriqueRepository.findAll().stream()
                .filter(psh -> psh.getPaiement() != null && psh.getPaiement().getIdPaiement().equals(paiementId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PaiementStatusHistoriqueDTO createPaiementStatusHistorique(PaiementStatusHistoriqueDTO dto) {
        PaiementStatusHistorique entity = convertToEntity(dto);
        PaiementStatusHistorique saved = paiementStatusHistoriqueRepository.save(entity);
        return convertToDTO(saved);
    }

    public PaiementStatusHistoriqueDTO updatePaiementStatusHistorique(Integer id, PaiementStatusHistoriqueDTO dto) {
        Optional<PaiementStatusHistorique> existing = paiementStatusHistoriqueRepository.findById(id);
        if (existing.isPresent()) {
            PaiementStatusHistorique entity = existing.get();
            
            if (dto.getIdPaiement() != null) {
                paiementRepository.findById(dto.getIdPaiement())
                    .ifPresent(entity::setPaiement);
            }
            
            if (dto.getIdStatus() != null) {
                statusRepository.findById(dto.getIdStatus())
                    .ifPresent(entity::setStatus);
            }
            
            entity.setDateModification(dto.getDateModification());
            PaiementStatusHistorique updated = paiementStatusHistoriqueRepository.save(entity);
            return convertToDTO(updated);
        }
        return null;
    }

    public boolean deletePaiementStatusHistorique(Integer id) {
        if (paiementStatusHistoriqueRepository.existsById(id)) {
            paiementStatusHistoriqueRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PaiementStatusHistoriqueDTO convertToDTO(PaiementStatusHistorique entity) {
        PaiementDTO paiementDTO = null;
        if (entity.getPaiement() != null) {
            paiementDTO = new PaiementDTO();
            paiementDTO.setIdPaiement(entity.getPaiement().getIdPaiement());
        }
        
        StatusDTO statusDTO = null;
        if (entity.getStatus() != null) {
            Status status = entity.getStatus();
            statusDTO = new StatusDTO(status.getIdStatus(), status.getLibelle(), 
                                      status.getCode(), status.getOrdre(), null);
        }
        
        return new PaiementStatusHistoriqueDTO(
            entity.getId(),
            paiementDTO,
            statusDTO,
            entity.getDateModification()
        );
    }

    private PaiementStatusHistorique convertToEntity(PaiementStatusHistoriqueDTO dto) {
        Paiement paiement = null;
        if (dto.getIdPaiement() != null) {
            paiement = paiementRepository.findById(dto.getIdPaiement()).orElse(null);
        }
        
        Status status = null;
        if (dto.getIdStatus() != null) {
            status = statusRepository.findById(dto.getIdStatus()).orElse(null);
        }
        
        return new PaiementStatusHistorique(paiement, status, dto.getDateModification());
    }
}
