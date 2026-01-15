package com.example.demo.service;

import com.example.demo.dto.MethodePaiementDTO;
import com.example.demo.dto.PaiementDTO;
import com.example.demo.dto.PaiementDetailDTO;
import com.example.demo.entity.MethodePaiement;
import com.example.demo.entity.Paiement;
import com.example.demo.entity.PaiementDetail;
import com.example.demo.repository.MethodePaiementRepository;
import com.example.demo.repository.PaiementDetailRepository;
import com.example.demo.repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaiementDetailService {
    @Autowired
    private PaiementDetailRepository paiementDetailRepository;
    
    @Autowired
    private PaiementRepository paiementRepository;
    
    @Autowired
    private MethodePaiementRepository methodePaiementRepository;

    public List<PaiementDetailDTO> getAllPaiementDetails() {
        return paiementDetailRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PaiementDetailDTO> getPaiementDetailById(Integer id) {
        return paiementDetailRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<PaiementDetailDTO> getByPaiementId(Integer paiementId) {
        return paiementDetailRepository.findAll().stream()
                .filter(pd -> pd.getPaiement() != null && pd.getPaiement().getIdPaiement().equals(paiementId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PaiementDetailDTO createPaiementDetail(PaiementDetailDTO dto) {
        PaiementDetail entity = convertToEntity(dto);
        PaiementDetail saved = paiementDetailRepository.save(entity);
        return convertToDTO(saved);
    }

    public PaiementDetailDTO updatePaiementDetail(Integer id, PaiementDetailDTO dto) {
        Optional<PaiementDetail> existing = paiementDetailRepository.findById(id);
        if (existing.isPresent()) {
            PaiementDetail entity = existing.get();
            
            if (dto.getIdPaiement() != null) {
                paiementRepository.findById(dto.getIdPaiement())
                    .ifPresent(entity::setPaiement);
            }
            
            if (dto.getIdMethodePaiement() != null) {
                methodePaiementRepository.findById(dto.getIdMethodePaiement())
                    .ifPresent(entity::setMethodePaiement);
            }
            
            entity.setMontant(dto.getMontant());
            entity.setDatePaiement(dto.getDatePaiement());
            PaiementDetail updated = paiementDetailRepository.save(entity);
            return convertToDTO(updated);
        }
        return null;
    }

    public boolean deletePaiementDetail(Integer id) {
        if (paiementDetailRepository.existsById(id)) {
            paiementDetailRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PaiementDetailDTO convertToDTO(PaiementDetail entity) {
        PaiementDTO paiementDTO = null;
        if (entity.getPaiement() != null) {
            paiementDTO = new PaiementDTO();
            paiementDTO.setIdPaiement(entity.getPaiement().getIdPaiement());
        }
        
        MethodePaiementDTO methodePaiementDTO = null;
        if (entity.getMethodePaiement() != null) {
            MethodePaiement mp = entity.getMethodePaiement();
            methodePaiementDTO = new MethodePaiementDTO(mp.getIdMethodePaiement(), mp.getLibelle());
        }
        
        return new PaiementDetailDTO(
            entity.getId(),
            paiementDTO,
            methodePaiementDTO,
            entity.getMontant(),
            entity.getDatePaiement()
        );
    }

    private PaiementDetail convertToEntity(PaiementDetailDTO dto) {
        Paiement paiement = null;
        if (dto.getIdPaiement() != null) {
            paiement = paiementRepository.findById(dto.getIdPaiement()).orElse(null);
        }
        
        MethodePaiement methodePaiement = null;
        if (dto.getIdMethodePaiement() != null) {
            methodePaiement = methodePaiementRepository.findById(dto.getIdMethodePaiement()).orElse(null);
        }
        
        return new PaiementDetail(paiement, methodePaiement, dto.getMontant(), dto.getDatePaiement());
    }
}
