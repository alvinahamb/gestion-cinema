package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaiementProduitService {
    @Autowired
    private PaiementProduitRepository paiementProduitRepository;
    
    @Autowired
    private PaiementProduitDetailRepository paiementProduitDetailRepository;
    
    @Autowired
    private VenteProduitDetailRepository venteProduitDetailRepository;

    public List<PaiementProduitDTO> getAllPaiements() {
        return paiementProduitRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PaiementProduitDTO> getPaiementById(Integer id) {
        return paiementProduitRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    public Optional<PaiementProduitDTO> getPaiementByVenteId(Integer venteId) {
        return paiementProduitRepository.findByVenteProduitId(venteId)
                .map(this::convertToDTO);
    }

    private PaiementProduitDTO convertToDTO(PaiementProduit paiement) {
        VenteProduitDTO venteDTO = null;
        if (paiement.getVenteProduit() != null) {
            VenteProduit vente = paiement.getVenteProduit();
            
            ClientDTO clientDTO = null;
            if (vente.getClient() != null) {
                Client c = vente.getClient();
                CategorieClientDTO categorieDTO = null;
                if (c.getCategorieClient() != null) {
                    categorieDTO = new CategorieClientDTO(
                        c.getCategorieClient().getIdCategorieClient(),
                        c.getCategorieClient().getLibelle(),
                        c.getCategorieClient().getDescription()
                    );
                }
                clientDTO = new ClientDTO(c.getIdClient(), c.getNom(), c.getPrenom(), 
                    c.getDateNaissance(), c.getEmail(), c.getTelephone(), categorieDTO);
            }
            
            SeanceDTO seanceDTO = null;
            if (vente.getSeance() != null) {
                Seance s = vente.getSeance();
                seanceDTO = new SeanceDTO();
                seanceDTO.setIdSeance(s.getIdSeance());
                seanceDTO.setDateSeance(s.getDateSeance());
                seanceDTO.setHeureDebut(s.getHeureDebut());
                if (s.getFilmVersion() != null && s.getFilmVersion().getFilm() != null) {
                    FilmVersionDTO fvDTO = new FilmVersionDTO();
                    fvDTO.setId(s.getFilmVersion().getId());
                    FilmDTO filmDTO = new FilmDTO();
                    filmDTO.setIdFilm(s.getFilmVersion().getFilm().getIdFilm());
                    filmDTO.setTitre(s.getFilmVersion().getFilm().getTitre());
                    fvDTO.setFilm(filmDTO);
                    seanceDTO.setFilmVersion(fvDTO);
                }
            }
            
            venteDTO = new VenteProduitDTO(vente.getId(), clientDTO, seanceDTO, vente.getTotal(), vente.getDateAchat());
            
            // Charger les détails de vente (nullable)
            List<VenteProduitDetailDTO> venteDetails = venteProduitDetailRepository.findByVenteProduitId(vente.getId()).stream()
                .map(detail -> {
                    ProduitDTO produitDTO = null;
                    if (detail.getProduit() != null) {
                        produitDTO = new ProduitDTO(
                            detail.getProduit().getId(),
                            detail.getProduit().getNom(),
                            detail.getProduit().getPrix()
                        );
                    }
                    return new VenteProduitDetailDTO(
                        detail.getId(),
                        detail.getVenteProduit().getId(),
                        produitDTO,
                        detail.getQuantite(),
                        detail.getPrixUnitaire()
                    );
                })
                .collect(Collectors.toList());
            venteDTO.setDetails(venteDetails);
        }
        
        PaiementProduitDTO dto = new PaiementProduitDTO(
            paiement.getId(),
            venteDTO,
            paiement.getMontantPaye(),
            paiement.getMontantRestant(),
            paiement.getDatePaiement()
        );
        
        // Charger les détails de paiement
        List<PaiementProduitDetailDTO> detailsDTO = paiementProduitDetailRepository.findByPaiementProduitId(paiement.getId()).stream()
            .map(detail -> new PaiementProduitDetailDTO(
                detail.getId(),
                detail.getPaiementProduit().getId(),
                detail.getMontant(),
                detail.getDatePaiement()
            ))
            .collect(Collectors.toList());
        dto.setDetails(detailsDTO);
        
        return dto;
    }
}
