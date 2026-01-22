package com.example.demo.service;

import com.example.demo.dto.TarifDiffusionDTO;
import com.example.demo.entity.TarifDiffusion;
import com.example.demo.repository.TarifDiffusionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarifDiffusionService {
    @Autowired
    private TarifDiffusionRepository tarifDiffusionRepository;

    public List<TarifDiffusionDTO> getAllTarifDiffusions() {
        return tarifDiffusionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TarifDiffusionDTO> getTarifDiffusionById(Integer id) {
        return tarifDiffusionRepository.findById(id)
                .map(this::convertToDTO);
    }

    public TarifDiffusionDTO createTarifDiffusion(TarifDiffusionDTO tarifDiffusionDTO) {
        TarifDiffusion tarifDiffusion = convertToEntity(tarifDiffusionDTO);
        if (tarifDiffusion.getDateAjout() == null) {
            tarifDiffusion.setDateAjout(LocalDate.now());
        }
        TarifDiffusion savedTarifDiffusion = tarifDiffusionRepository.save(tarifDiffusion);
        return convertToDTO(savedTarifDiffusion);
    }

    public TarifDiffusionDTO updateTarifDiffusion(Integer id, TarifDiffusionDTO tarifDiffusionDTO) {
        Optional<TarifDiffusion> existingTarifDiffusion = tarifDiffusionRepository.findById(id);
        if (existingTarifDiffusion.isPresent()) {
            TarifDiffusion tarifDiffusion = existingTarifDiffusion.get();
            tarifDiffusion.setTarif(tarifDiffusionDTO.getTarif());
            TarifDiffusion updatedTarifDiffusion = tarifDiffusionRepository.save(tarifDiffusion);
            return convertToDTO(updatedTarifDiffusion);
        }
        return null;
    }

    public boolean deleteTarifDiffusion(Integer id) {
        if (tarifDiffusionRepository.existsById(id)) {
            tarifDiffusionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private TarifDiffusionDTO convertToDTO(TarifDiffusion tarifDiffusion) {
        return new TarifDiffusionDTO(
            tarifDiffusion.getId(),
            tarifDiffusion.getTarif(),
            tarifDiffusion.getDateAjout()
        );
    }

    private TarifDiffusion convertToEntity(TarifDiffusionDTO dto) {
        return new TarifDiffusion(
            dto.getTarif(),
            dto.getDateAjout()
        );
    }
}
