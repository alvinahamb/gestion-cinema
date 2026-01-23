package com.example.demo.service;

import com.example.demo.dto.FactureSocieteDTO;
import com.example.demo.dto.FactureSocieteDetailDTO;
import com.example.demo.entity.FactureSociete;
import com.example.demo.entity.FactureSocieteDetail;
import com.example.demo.repository.FactureSocieteRepository;
import com.example.demo.repository.FactureSocieteDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FactureSocieteService {

    @Autowired
    private FactureSocieteRepository factureSocieteRepository;

    @Autowired
    private FactureSocieteDetailRepository factureSocieteDetailRepository;

    public List<FactureSocieteDTO> getAllFactures() {
        return factureSocieteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<FactureSocieteDTO> getFacturesBySocieteId(Integer idSociete) {
        return factureSocieteRepository.findBySocieteId(idSociete).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private FactureSocieteDTO convertToDTO(FactureSociete entity) {
        FactureSocieteDTO dto = new FactureSocieteDTO();
        dto.setId(entity.getId());
        dto.setTotal(entity.getTotal() != null ? entity.getTotal() : BigDecimal.ZERO);
        dto.setPourcentageRestante(entity.getPourcentageRestante());
        dto.setDateFacture(entity.getDateFacture());
        dto.setDatePaiement(entity.getDatePaiement());
        dto.setDateAjout(entity.getDateAjout());

        if (entity.getSociete() != null) {
            dto.setIdSociete(entity.getSociete().getId());
            dto.setNomSociete(entity.getSociete().getNom());
        }

        // Calculer le montant payé
        BigDecimal montantPaye = factureSocieteDetailRepository.sumMontantByFactureSocieteId(entity.getId());
        dto.setMontantPaye(montantPaye != null ? montantPaye : BigDecimal.ZERO);

        // Calculer le montant restant
        BigDecimal total = dto.getTotal() != null ? dto.getTotal() : BigDecimal.ZERO;
        dto.setMontantRestant(total.subtract(dto.getMontantPaye()));

        // Récupérer les détails
        List<FactureSocieteDetailDTO> details = factureSocieteDetailRepository.findByFactureSocieteId(entity.getId())
                .stream()
                .map(this::convertDetailToDTO)
                .collect(Collectors.toList());
        dto.setDetails(details);

        return dto;
    }

    private FactureSocieteDetailDTO convertDetailToDTO(FactureSocieteDetail entity) {
        FactureSocieteDetailDTO dto = new FactureSocieteDetailDTO();
        dto.setId(entity.getId());
        dto.setMontant(entity.getMontant());
        dto.setPourcentagePaye(entity.getPourcentagePaye());
        dto.setDatePaiement(entity.getDatePaiement());
        if (entity.getFactureSociete() != null) {
            dto.setIdFactureSociete(entity.getFactureSociete().getId());
        }
        return dto;
    }

    /**
     * Ajoute un paiement à une facture société.
     * Calcule automatiquement le pourcentage payé = (montant / total facture) * 100
     * Met à jour le pourcentage restant de la facture = 100 - somme des pourcentages payés
     */
    public FactureSocieteDetailDTO addPaiement(Integer idFactureSociete, BigDecimal montant) {
        Optional<FactureSociete> factureOpt = factureSocieteRepository.findById(idFactureSociete);
        if (factureOpt.isEmpty()) {
            throw new RuntimeException("Facture non trouvée");
        }
        
        FactureSociete facture = factureOpt.get();
        BigDecimal total = facture.getTotal() != null ? facture.getTotal() : BigDecimal.ZERO;
        
        // Calculer le pourcentage payé pour ce paiement
        BigDecimal pourcentagePaye = BigDecimal.ZERO;
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            pourcentagePaye = montant.multiply(new BigDecimal("100")).divide(total, 2, java.math.RoundingMode.HALF_UP);
        }
        
        // Créer le détail de paiement
        FactureSocieteDetail detail = new FactureSocieteDetail();
        detail.setFactureSociete(facture);
        detail.setMontant(montant);
        detail.setPourcentagePaye(pourcentagePaye);
        detail.setDatePaiement(java.time.LocalDate.now());
        
        FactureSocieteDetail saved = factureSocieteDetailRepository.save(detail);
        
        // Mettre à jour le pourcentage restant de la facture
        updatePourcentageRestant(facture);
        
        return convertDetailToDTO(saved);
    }
    
    /**
     * Met à jour le pourcentage restant de la facture = 100 - somme des pourcentages payés
     */
    private void updatePourcentageRestant(FactureSociete facture) {
        BigDecimal sommePourcentages = factureSocieteDetailRepository.sumPourcentagePayeByFactureSocieteId(facture.getId());
        if (sommePourcentages == null) {
            sommePourcentages = BigDecimal.ZERO;
        }
        BigDecimal pourcentageRestant = new BigDecimal("100").subtract(sommePourcentages);
        if (pourcentageRestant.compareTo(BigDecimal.ZERO) < 0) {
            pourcentageRestant = BigDecimal.ZERO;
        }
        facture.setPourcentageRestante(pourcentageRestant);
        factureSocieteRepository.save(facture);
    }
    
    public Optional<FactureSociete> getFactureById(Integer id) {
        return factureSocieteRepository.findById(id);
    }
}
