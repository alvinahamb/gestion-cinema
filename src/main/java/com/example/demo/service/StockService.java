package com.example.demo.service;

import com.example.demo.dto.MouvementStockDTO;
import com.example.demo.dto.ProduitDTO;
import com.example.demo.dto.StockDTO;
import com.example.demo.dto.VenteProduitDetailDTO;
import com.example.demo.entity.MouvementStock;
import com.example.demo.entity.Produit;
import com.example.demo.entity.Stock;
import com.example.demo.entity.VenteProduitDetail;
import com.example.demo.repository.MouvementStockRepository;
import com.example.demo.repository.ProduitRepository;
import com.example.demo.repository.StockRepository;
import com.example.demo.repository.VenteProduitDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StockService {
    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private ProduitRepository produitRepository;
    
    @Autowired
    private MouvementStockRepository mouvementStockRepository;
    
    @Autowired
    private VenteProduitDetailRepository venteProduitDetailRepository;

    public List<StockDTO> getAllStocks() {
        return stockRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<StockDTO> getStockById(Integer id) {
        return stockRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    public Optional<StockDTO> getStockByProduitId(Integer produitId) {
        return stockRepository.findByProduitId(produitId)
                .map(this::convertToDTO);
    }

    public StockDTO updateStock(Integer produitId, Integer quantite) {
        Optional<Stock> stockOpt = stockRepository.findByProduitId(produitId);
        if (stockOpt.isPresent()) {
            Stock stock = stockOpt.get();
            stock.setQuantiteActuelle(quantite);
            return convertToDTO(stockRepository.save(stock));
        }
        return null;
    }
    
    // Ajouter une entrée de stock
    public MouvementStockDTO ajouterEntree(Integer produitId, Integer quantite) {
        Optional<Stock> stockOpt = stockRepository.findByProduitId(produitId);
        Optional<Produit> produitOpt = produitRepository.findById(produitId);
        
        if (stockOpt.isPresent() && produitOpt.isPresent()) {
            Stock stock = stockOpt.get();
            int nouvelleQuantite = stock.getQuantiteActuelle() + quantite;
            stock.setQuantiteActuelle(nouvelleQuantite);
            stockRepository.save(stock);
            
            // Créer le mouvement
            MouvementStock mouvement = new MouvementStock(
                produitOpt.get(),
                quantite,
                0,
                nouvelleQuantite,
                LocalDate.now(),
                null
            );
            MouvementStock savedMouvement = mouvementStockRepository.save(mouvement);
            return convertMouvementToDTO(savedMouvement);
        }
        return null;
    }
    
    // Retirer du stock (sortie)
    public MouvementStockDTO retirerStock(Integer produitId, Integer quantite, VenteProduitDetail venteProduitDetail) {
        Optional<Stock> stockOpt = stockRepository.findByProduitId(produitId);
        Optional<Produit> produitOpt = produitRepository.findById(produitId);
        
        if (stockOpt.isPresent() && produitOpt.isPresent()) {
            Stock stock = stockOpt.get();
            int nouvelleQuantite = stock.getQuantiteActuelle() - quantite;
            stock.setQuantiteActuelle(nouvelleQuantite);
            stockRepository.save(stock);
            
            // Créer le mouvement
            MouvementStock mouvement = new MouvementStock(
                produitOpt.get(),
                0,
                quantite,
                nouvelleQuantite,
                LocalDate.now(),
                venteProduitDetail
            );
            MouvementStock savedMouvement = mouvementStockRepository.save(mouvement);
            return convertMouvementToDTO(savedMouvement);
        }
        return null;
    }
    
    public List<MouvementStockDTO> getMouvementsByProduitId(Integer produitId) {
        return mouvementStockRepository.findByProduitId(produitId).stream()
                .map(this::convertMouvementToDTO)
                .collect(Collectors.toList());
    }
    
    public List<MouvementStockDTO> getAllMouvements() {
        return mouvementStockRepository.findAllOrderByDateDesc().stream()
                .map(this::convertMouvementToDTO)
                .collect(Collectors.toList());
    }

    private StockDTO convertToDTO(Stock stock) {
        ProduitDTO produitDTO = null;
        if (stock.getProduit() != null) {
            produitDTO = new ProduitDTO(
                stock.getProduit().getId(),
                stock.getProduit().getNom(),
                stock.getProduit().getPrix()
            );
        }
        return new StockDTO(stock.getId(), produitDTO, stock.getQuantiteActuelle());
    }
    
    private MouvementStockDTO convertMouvementToDTO(MouvementStock mouvement) {
        ProduitDTO produitDTO = null;
        if (mouvement.getProduit() != null) {
            produitDTO = new ProduitDTO(
                mouvement.getProduit().getId(),
                mouvement.getProduit().getNom(),
                mouvement.getProduit().getPrix()
            );
        }
        
        VenteProduitDetailDTO detailDTO = null;
        if (mouvement.getVenteProduitDetail() != null) {
            VenteProduitDetail detail = mouvement.getVenteProduitDetail();
            ProduitDTO detailProduitDTO = null;
            if (detail.getProduit() != null) {
                detailProduitDTO = new ProduitDTO(
                    detail.getProduit().getId(),
                    detail.getProduit().getNom(),
                    detail.getProduit().getPrix()
                );
            }
            detailDTO = new VenteProduitDetailDTO(
                detail.getId(),
                detail.getVenteProduit() != null ? detail.getVenteProduit().getId() : null,
                detailProduitDTO,
                detail.getQuantite(),
                detail.getPrixUnitaire()
            );
        }
        
        return new MouvementStockDTO(
            mouvement.getId(),
            produitDTO,
            mouvement.getEntree(),
            mouvement.getSortie(),
            mouvement.getQuantiteActuelle(),
            mouvement.getDateMouvement(),
            detailDTO
        );
    }
}
