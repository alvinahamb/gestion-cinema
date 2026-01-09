package com.example.demo.service;

import com.example.demo.dto.LangueDTO;
import com.example.demo.entity.Langue;
import com.example.demo.repository.LangueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LangueService {
    @Autowired
    private LangueRepository languageRepository;

    public List<LangueDTO> getAllLangues() {
        return languageRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<LangueDTO> getLangueById(Integer id) {
        return languageRepository.findById(id)
                .map(this::convertToDTO);
    }

    public LangueDTO createLangue(LangueDTO languageDTO) {
        Langue langue = convertToEntity(languageDTO);
        Langue savedLanguage = languageRepository.save(langue);
        return convertToDTO(savedLanguage);
    }

    public LangueDTO updateLangue(Integer id, LangueDTO languageDTO) {
        Optional<Langue> existingLanguage = languageRepository.findById(id);
        if (existingLanguage.isPresent()) {
            Langue langue = existingLanguage.get();
            langue.setNom(languageDTO.getNom());
            langue.setCode(languageDTO.getCode());
            Langue updatedLanguage = languageRepository.save(langue);
            return convertToDTO(updatedLanguage);
        }
        return null;
    }

    public boolean deleteLangue(Integer id) {
        if (languageRepository.existsById(id)) {
            languageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private LangueDTO convertToDTO(Langue langue) {
        return new LangueDTO(langue.getIdLangue(), langue.getNom(), langue.getCode());
    }

    private Langue convertToEntity(LangueDTO languageDTO) {
        return new Langue(languageDTO.getNom(), languageDTO.getCode());
    }
}
