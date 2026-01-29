package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VenteProduitService {
    @Autowired
    private VenteProduitRepository venteProduitRepository;
    
    @Autowired
    private VenteProduitDetailRepository venteProduitDetailRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private SeanceRepository seanceRepository;
    
    @Autowired
    private ProduitRepository produitRepository;
    
    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private MouvementStockRepository mouvementStockRepository;
    
    @Autowired
    private PaiementProduitRepository paiementProduitRepository;
    
    @Autowired
    private PaiementProduitDetailRepository paiementProduitDetailRepository;

    public List<VenteProduitDTO> getAllVentes() {
        return venteProduitRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<VenteProduitDTO> getVenteById(Integer id) {
        return venteProduitRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    public List<VenteProduitDTO> getVentesBySeanceId(Integer seanceId) {
        return venteProduitRepository.findBySeanceId(seanceId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public BigDecimal getTotalVentesBySeanceId(Integer seanceId) {
        return venteProduitRepository.sumTotalBySeanceId(seanceId);
    }
    
    public BigDecimal getTotalAllVentes() {
        return venteProduitRepository.sumAllVenteProduits();
    }

    /**
     * Créer une vente de produits avec paiement automatique
     */
    public VenteProduitDTO createVente(Integer clientId, Integer seanceId, List<VenteProduitDetailDTO> details) {
        // Récupérer le client et la séance
        Optional<Client> clientOpt = clientRepository.findById(clientId);
        Optional<Seance> seanceOpt = seanceId != null ? seanceRepository.findById(seanceId) : Optional.empty();
        
        if (clientOpt.isEmpty()) {
            throw new RuntimeException("Client non trouvé");
        }
        
        // Calculer le total
        BigDecimal total = BigDecimal.ZERO;
        for (VenteProduitDetailDTO detail : details) {
            Optional<Produit> produitOpt = produitRepository.findById(detail.getProduitId());
            if (produitOpt.isPresent()) {
                BigDecimal prixUnitaire = produitOpt.get().getPrix();
                total = total.add(prixUnitaire.multiply(BigDecimal.valueOf(detail.getQuantite())));
            }
        }
        
        // Créer la vente
        VenteProduit vente = new VenteProduit();
        vente.setClient(clientOpt.get());
        vente.setSeance(seanceOpt.orElse(null));
        vente.setTotal(total);
        vente.setDateAchat(LocalDate.now());
        
        VenteProduit savedVente = venteProduitRepository.save(vente);
        
        // Créer les détails et mettre à jour le stock
        for (VenteProduitDetailDTO detailDTO : details) {
            Optional<Produit> produitOpt = produitRepository.findById(detailDTO.getProduitId());
            if (produitOpt.isPresent()) {
                Produit produit = produitOpt.get();
                
                // Créer le détail de vente
                VenteProduitDetail detail = new VenteProduitDetail();
                detail.setVenteProduit(savedVente);
                detail.setProduit(produit);
                detail.setQuantite(detailDTO.getQuantite());
                detail.setPrixUnitaire(produit.getPrix());
                VenteProduitDetail savedDetail = venteProduitDetailRepository.save(detail);
                
                // Mettre à jour le stock
                Optional<Stock> stockOpt = stockRepository.findByProduitId(produit.getId());
                if (stockOpt.isPresent()) {
                    Stock stock = stockOpt.get();
                    int nouvelleQuantite = stock.getQuantiteActuelle() - detailDTO.getQuantite();
                    stock.setQuantiteActuelle(nouvelleQuantite);
                    stockRepository.save(stock);
                    
                    // Créer le mouvement de stock
                    MouvementStock mouvement = new MouvementStock(
                        produit,
                        0, // entree
                        detailDTO.getQuantite(), // sortie
                        nouvelleQuantite,
                        LocalDate.now(),
                        savedDetail
                    );
                    mouvementStockRepository.save(mouvement);
                }
            }
        }
        
        // Créer le paiement automatiquement
        PaiementProduit paiement = new PaiementProduit();
        paiement.setVenteProduit(savedVente);
        paiement.setMontantPaye(total);
        paiement.setMontantRestant(BigDecimal.ZERO);
        paiement.setDatePaiement(LocalDate.now());
        PaiementProduit savedPaiement = paiementProduitRepository.save(paiement);
        
        // Créer le détail de paiement
        PaiementProduitDetail paiementDetail = new PaiementProduitDetail();
        paiementDetail.setPaiementProduit(savedPaiement);
        paiementDetail.setMontant(total);
        paiementDetail.setDatePaiement(LocalDate.now());
        paiementProduitDetailRepository.save(paiementDetail);
        
        return convertToDTO(savedVente);
    }

    public boolean deleteVente(Integer id) {
        if (venteProduitRepository.existsById(id)) {
            // Supprimer les détails de paiement et paiement
            paiementProduitRepository.findByVenteProduitId(id).ifPresent(paiement -> {
                paiementProduitDetailRepository.findByPaiementProduitId(paiement.getId())
                    .forEach(detail -> paiementProduitDetailRepository.delete(detail));
                paiementProduitRepository.delete(paiement);
            });
            
            // Supprimer les détails de vente
            venteProduitDetailRepository.findByVenteProduitId(id)
                .forEach(detail -> venteProduitDetailRepository.delete(detail));
            
            venteProduitRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private VenteProduitDTO convertToDTO(VenteProduit vente) {
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
        
        VenteProduitDTO dto = new VenteProduitDTO(vente.getId(), clientDTO, seanceDTO, vente.getTotal(), vente.getDateAchat());
        
        // Charger les détails
        List<VenteProduitDetailDTO> detailsDTO = venteProduitDetailRepository.findByVenteProduitId(vente.getId()).stream()
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
        dto.setDetails(detailsDTO);
        
        return dto;
    }
}
