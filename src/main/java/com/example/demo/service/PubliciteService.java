package com.example.demo.service;

import com.example.demo.dto.PubliciteDTO;
import com.example.demo.entity.Publicite;
import com.example.demo.repository.PubliciteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PubliciteService {
    @Autowired
    private PubliciteRepository publiciteRepository;

    public List<PubliciteDTO> getAllPublicites() {
        return publiciteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PubliciteDTO> getPubliciteById(Integer id) {
        return publiciteRepository.findById(id)
                .map(this::convertToDTO);
    }

    public PubliciteDTO createPublicite(PubliciteDTO publiciteDTO) {
        Publicite publicite = convertToEntity(publiciteDTO);
        if (publicite.getDateAjout() == null) {
            publicite.setDateAjout(LocalDate.now());
        }
        Publicite savedPublicite = publiciteRepository.save(publicite);
        return convertToDTO(savedPublicite);
    }

    public PubliciteDTO updatePublicite(Integer id, PubliciteDTO publiciteDTO) {
        Optional<Publicite> existingPublicite = publiciteRepository.findById(id);
        if (existingPublicite.isPresent()) {
            Publicite publicite = existingPublicite.get();
            publicite.setTitre(publiciteDTO.getTitre());
            publicite.setDuree(publiciteDTO.getDuree());
            publicite.setCode(publiciteDTO.getCode());
            Publicite updatedPublicite = publiciteRepository.save(publicite);
            return convertToDTO(updatedPublicite);
        }
        return null;
    }

    public boolean deletePublicite(Integer id) {
        if (publiciteRepository.existsById(id)) {
            publiciteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PubliciteDTO convertToDTO(Publicite publicite) {
        return new PubliciteDTO(
            publicite.getId(),
            publicite.getTitre(),
            publicite.getDuree(),
            publicite.getDateAjout(),
            publicite.getCode()
        );
    }

    private Publicite convertToEntity(PubliciteDTO dto) {
        return new Publicite(
            dto.getTitre(),
            dto.getDuree(),
            dto.getDateAjout(),
            dto.getCode()
        );
    }
}
