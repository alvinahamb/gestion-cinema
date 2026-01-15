package com.example.demo.service;

import com.example.demo.dto.CategorieClientDTO;
import com.example.demo.entity.CategorieClient;
import com.example.demo.repository.CategorieClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategorieClientService {
    @Autowired
    private CategorieClientRepository categorieClientRepository;

    public List<CategorieClientDTO> getAllCategorieClients() {
        return categorieClientRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategorieClientDTO> getCategorieClientById(Integer id) {
        return categorieClientRepository.findById(id)
                .map(this::convertToDTO);
    }

    public CategorieClientDTO createCategorieClient(CategorieClientDTO categorieClientDTO) {
        CategorieClient categorieClient = convertToEntity(categorieClientDTO);
        CategorieClient savedCategorieClient = categorieClientRepository.save(categorieClient);
        return convertToDTO(savedCategorieClient);
    }

    public CategorieClientDTO updateCategorieClient(Integer id, CategorieClientDTO categorieClientDTO) {
        Optional<CategorieClient> existingCategorieClient = categorieClientRepository.findById(id);
        if (existingCategorieClient.isPresent()) {
            CategorieClient categorieClient = existingCategorieClient.get();
            categorieClient.setLibelle(categorieClientDTO.getLibelle());
            categorieClient.setDescription(categorieClientDTO.getDescription());
            CategorieClient updatedCategorieClient = categorieClientRepository.save(categorieClient);
            return convertToDTO(updatedCategorieClient);
        }
        return null;
    }

    public boolean deleteCategorieClient(Integer id) {
        if (categorieClientRepository.existsById(id)) {
            categorieClientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private CategorieClientDTO convertToDTO(CategorieClient categorieClient) {
        return new CategorieClientDTO(categorieClient.getIdCategorieClient(), categorieClient.getLibelle(),
                                    categorieClient.getDescription());
    }

    private CategorieClient convertToEntity(CategorieClientDTO categorieClientDTO) {
        return new CategorieClient(categorieClientDTO.getLibelle(), categorieClientDTO.getDescription());
    }
}