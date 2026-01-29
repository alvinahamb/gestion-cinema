package com.example.demo.service;

import com.example.demo.dto.ProduitDTO;
import com.example.demo.entity.Produit;
import com.example.demo.entity.Stock;
import com.example.demo.repository.ProduitRepository;
import com.example.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProduitService {
    @Autowired
    private ProduitRepository produitRepository;
    
    @Autowired
    private StockRepository stockRepository;

    public List<ProduitDTO> getAllProduits() {
        return produitRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProduitDTO> getProduitById(Integer id) {
        return produitRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ProduitDTO createProduit(ProduitDTO produitDTO) {
        Produit produit = convertToEntity(produitDTO);
        Produit savedProduit = produitRepository.save(produit);
        
        // Créer un stock initial à 0 pour le nouveau produit
        Stock stock = new Stock(savedProduit, 0);
        stockRepository.save(stock);
        
        return convertToDTO(savedProduit);
    }

    public ProduitDTO updateProduit(Integer id, ProduitDTO produitDTO) {
        Optional<Produit> existingProduit = produitRepository.findById(id);
        if (existingProduit.isPresent()) {
            Produit produit = existingProduit.get();
            produit.setNom(produitDTO.getNom());
            produit.setPrix(produitDTO.getPrix());
            Produit updatedProduit = produitRepository.save(produit);
            return convertToDTO(updatedProduit);
        }
        return null;
    }

    public boolean deleteProduit(Integer id) {
        if (produitRepository.existsById(id)) {
            // Supprimer le stock associé
            stockRepository.findByProduitId(id).ifPresent(stock -> stockRepository.delete(stock));
            produitRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ProduitDTO convertToDTO(Produit produit) {
        return new ProduitDTO(produit.getId(), produit.getNom(), produit.getPrix());
    }

    private Produit convertToEntity(ProduitDTO produitDTO) {
        return new Produit(produitDTO.getNom(), produitDTO.getPrix());
    }
}
