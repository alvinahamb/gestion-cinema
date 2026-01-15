package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaiementService {
    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private PaiementDetailRepository paiementDetailRepository;

    @Autowired
    private PaiementStatusHistoriqueService paiementStatusHistoriqueService;

    @Autowired
    private MethodePaiementRepository methodePaiementRepository;

    public List<PaiementDTO> getAllPaiements() {
        return paiementRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PaiementDTO> getPaiementById(Integer id) {
        return paiementRepository.findById(id)
                .map(this::convertToDTO);
    }

    public Optional<PaiementDTO> getPaiementByTicketId(Integer ticketId) {
        return paiementRepository.findAll().stream()
                .filter(p -> p.getTicket() != null && p.getTicket().getIdTicket().equals(ticketId))
                .findFirst()
                .map(this::convertToDTO);
    }

    public PaiementDTO createPaiement(PaiementDTO paiementDTO) {
        Paiement paiement = convertToEntity(paiementDTO);
        Paiement savedPaiement = paiementRepository.save(paiement);
        return convertToDTO(savedPaiement);
    }

    public PaiementDTO updatePaiement(Integer id, PaiementDTO paiementDTO) {
        Optional<Paiement> existingPaiement = paiementRepository.findById(id);
        if (existingPaiement.isPresent()) {
            Paiement paiement = existingPaiement.get();
            paiement.setMontant(paiementDTO.getMontant());
            paiement.setCreerLe(paiementDTO.getCreerLe());
            paiement.setDatePaiementPrevu(paiementDTO.getDatePaiementPrevu());
            
            Paiement updatedPaiement = paiementRepository.save(paiement);
            return convertToDTO(updatedPaiement);
        }
        return null;
    }

    public boolean deletePaiement(Integer id) {
        if (paiementRepository.existsById(id)) {
            paiementRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PaiementDTO convertToDTO(Paiement paiement) {
        TicketDTO ticket = null;
        if (paiement.getTicket() != null) {
            ticket = ticketService.convertToDTO(paiement.getTicket());
        }
        StatusDTO status = null;
        if (paiement.getStatus() != null) {
            TypeStatutDTO typeStatut = paiement.getStatus().getTypeStatut() != null ? 
                new TypeStatutDTO(paiement.getStatus().getTypeStatut().getIdTypeStatut(), paiement.getStatus().getTypeStatut().getLibelle()) : null;
            status = new StatusDTO(paiement.getStatus().getIdStatus(), paiement.getStatus().getLibelle(), 
                                 paiement.getStatus().getCode(), paiement.getStatus().getOrdre(), typeStatut);
        }
        return new PaiementDTO(
            paiement.getIdPaiement(),
            ticket,
            paiement.getMontant(),
            paiement.getCreerLe(),
            status,
            paiement.getDatePaiementPrevu()
        );
    }

    private Paiement convertToEntity(PaiementDTO paiementDTO) {
        Paiement paiement = new Paiement();
        if (paiementDTO.getTicket() != null) {
            ticketRepository.findById(paiementDTO.getTicket().getIdTicket()).ifPresent(paiement::setTicket);
        }
        paiement.setMontant(paiementDTO.getMontant());
        paiement.setCreerLe(paiementDTO.getCreerLe());
        if (paiementDTO.getStatus() != null) {
            statusRepository.findById(paiementDTO.getStatus().getIdStatus()).ifPresent(paiement::setStatus);
        }
        paiement.setDatePaiementPrevu(paiementDTO.getDatePaiementPrevu());
        return paiement;
    }

    /**
     * Traite un paiement partiel ou complet
     */
    public void processPaiement(Integer idPaiement, Integer idMethodePaiement, 
                               java.math.BigDecimal montant, java.time.LocalDate datePaiement) {
        Optional<Paiement> paiementOpt = paiementRepository.findById(idPaiement);
        if (paiementOpt.isEmpty()) {
            throw new RuntimeException("Paiement non trouvé");
        }

        Paiement paiement = paiementOpt.get();
        java.math.BigDecimal totalTicket = paiement.getTicket().getTotal();
        
        // Créer le détail de paiement
        PaiementDetail detail = new PaiementDetail();
        detail.setPaiement(paiement);
        detail.setMontant(montant);
        detail.setDatePaiement(datePaiement);
        
        // Récupérer la méthode de paiement
        methodePaiementRepository.findById(idMethodePaiement).ifPresent(detail::setMethodePaiement);
        
        paiementDetailRepository.save(detail);

        // Calculer le total payé jusqu'à présent
        java.math.BigDecimal totalPaye = paiementDetailRepository.findAll().stream()
                .filter(pd -> pd.getPaiement().getIdPaiement().equals(idPaiement))
                .map(PaiementDetail::getMontant)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        // Déterminer le nouveau statut
        Status nouveauStatus;
        if (totalPaye.compareTo(totalTicket) >= 0) {
            // Paiement complet
            nouveauStatus = statusRepository.findByCode("PAIEMENT_PAYE")
                    .orElseThrow(() -> new RuntimeException("Statut PAIEMENT_PAYE non trouvé"));
        } else {
            // Paiement partiel
            nouveauStatus = statusRepository.findByCode("PAIEMENT_PARTIEL")
                    .orElseThrow(() -> new RuntimeException("Statut PAIEMENT_PARTIEL non trouvé"));
        }

        // Si le statut change, enregistrer dans l'historique
        boolean statutChange = false;
        if (paiement.getStatus() == null) {
            // Premier paiement, toujours créer l'historique
            statutChange = true;
        } else if (!paiement.getStatus().getIdStatus().equals(nouveauStatus.getIdStatus())) {
            // Statut différent, créer l'historique
            statutChange = true;
        }

        if (statutChange) {
            StatusDTO statusDTO = new StatusDTO(
                nouveauStatus.getIdStatus(),
                nouveauStatus.getLibelle(),
                nouveauStatus.getCode(),
                nouveauStatus.getOrdre(),
                nouveauStatus.getTypeStatut() != null ? new com.example.demo.dto.TypeStatutDTO(
                    nouveauStatus.getTypeStatut().getIdTypeStatut(),
                    nouveauStatus.getTypeStatut().getLibelle()
                ) : null
            );
            
            paiementStatusHistoriqueService.createPaiementStatusHistorique(
                new com.example.demo.dto.PaiementStatusHistoriqueDTO(
                    null, 
                    convertToDTO(paiement), 
                    statusDTO, 
                    java.time.LocalDate.now()
                )
            );
            
            // Mettre à jour le statut du paiement
            paiement.setStatus(nouveauStatus);
            paiementRepository.save(paiement);
        }
    }
}
